<template>
  <div id="home">
    <div class="contanier-box container">
      <TableSearch
        :query="queryParam"
        :options="searchOption"
        :search="handleSearch"
        :change="loadData"
      />

      <el-skeleton v-if="isLoading" animated class="skeleton-parent">
        <template #template>
          <el-skeleton-item variant="rect" class="contanier-box" />
        </template>
      </el-skeleton>
      <TableCustom
        v-else
        :columns="columns"
        :table-data="tableData"
        :total="total"
        :current-page="queryParam.current"
        :page-size="queryParam.pageSize"
        :refresh="loadData"
        :change-page="changePage"
      >
        <template #content="{ rows }">
          <el-link :href="`/detail/${rows.id}`" target="_parent">
            <el-text truncated style="width: 400px">
              {{ rows.content }}
            </el-text>
          </el-link>
        </template>

        <template #type="{ rows }">
          <el-tag v-if="rows.type === 'single_answer'" type="primary">
            单选题
          </el-tag>
          <el-tag v-else-if="rows.type === 'multi_answer'" type="success">
            多选题
          </el-tag>
          <el-tag v-else-if="rows.type === 'true_false'" type="info">
            判断题
          </el-tag>
          <el-tag v-else-if="rows.type === 'fill_in_the_blank'" type="warning">
            填空题
          </el-tag>
          <el-tag v-else-if="rows.type === 'question_answer'" type="danger">
            问答题
          </el-tag>
        </template>

        <template #knowledge="{ rows }">
          <el-tag v-for="item in rows.knowledge">{{ item }}</el-tag>
        </template>
      </TableCustom>
    </div>
  </div>
</template>

<script setup lang="ts">
import { QuestionVO, QuestionControllerService } from "@/api-client";
import { ElMessage } from "element-plus";
import { FormOptionList } from "@/model/form";

const isLoading = ref(false);
type QueryQuestion = {
  current: number;
  pageSize: number;
  type?: string;
  knowledge?: string;
};
const total = ref(100);
const queryParam = ref<QueryQuestion>({
  current: 1,
  pageSize: 20,
  type: "all",
  knowledge: "all",
});

const searchOption = ref<FormOptionList[]>([
  {
    type: "select",
    prop: "type",
    label: "题型",
    opts: [],
  },
  {
    type: "select",
    prop: "knowledge",
    label: "考点",
    opts: [],
  },
]);
const handleSearch = () => {
  loadData();
};
const tableData = ref<QuestionVO[]>([]);
const columns = ref([
  {
    type: "index",
    label: "题号",
    width: 55,
    align: "center",
  },
  {
    prop: "type",
    label: "题型",
  },
  {
    prop: "content",
    label: "题目描述",
  },

  {
    prop: "difficulty",
    label: "难度",
  },
  {
    prop: "source",
    label: "来源",
  },
  {
    prop: "knowledge",
    label: "考点",
  },
]);

const changePage = (e) => {
  queryParam.value.current = e;
  loadData();
};
onMounted(() => {
  loadData();
});

const loadData = async () => {
  isLoading.value = true;
  try {
    await loadTypeData();
    await loadKnowledgeData();
    await loadTableData();
  } catch (err) {
    ElMessage({ type: "error", message: "数据请求异常" + err });
  } finally {
    isLoading.value = false;
  }

  isLoading.value = false;
};

const loadTypeData = async () => {
  const res = await QuestionControllerService.types();
  if (res.code === 0) {
    const opts = [{ label: "全部", value: "all" }, ...(res.data as any)];
    searchOption.value[0].opts = opts;
  } else {
    ElMessage({
      type: "error",
      message: res.message,
    });
  }
};

const loadKnowledgeData = async () => {
  const res = await QuestionControllerService.knowledge();

  if (res.code === 0) {
    const opts = [{ label: "全部", value: "all" }, ...(res.data as any)];
    searchOption.value[1].opts = opts;
  } else {
    ElMessage({
      type: "error",
      message: res.message,
    });
  }
};
const loadTableData = async () => {
  const tableRes = await QuestionControllerService.questionVoPage(
    queryParam.value.current,
    queryParam.value.pageSize,
    queryParam.value.type === "all" ? undefined : queryParam.value.type,
    queryParam.value.knowledge === "all"
      ? undefined
      : queryParam.value.knowledge
  );
  if (tableRes.code === 0) {
    tableData.value = tableRes.data?.records ?? [];
    total.value = tableRes.data?.total ?? 0;
  } else {
    ElMessage({
      type: "error",
      message: tableRes.message,
    });
  }
};
</script>

<style scoped lang="less">
.skeleton-parent {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
