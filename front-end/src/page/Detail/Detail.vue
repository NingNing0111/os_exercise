<template>
  <el-skeleton v-if="isLoading" animated>
    <template #template>
      <el-skeleton-item variant="rect" style="height: 100vh; width: 100%" />
    </template>
  </el-skeleton>
  <div v-else id="detail">
    <div id="detail-left"></div>
    <div id="detail-main">
      <VueMarkdownIt
        :source="questionVO.content ?? ''"
        :options="markdownItOption"
        preset="commonmark"
        :md-wrapper-class="`question-Markdown-Class`"
      />

      <el-divider />
      <el-form :inline="true" class="user-form">
        <!-- 题型判断 -->
        <el-radio-group
          v-model="userAns"
          v-if="
            questionVO.type === 'single_answer' ||
            questionVO.type === 'true_false'
          "
        >
          <el-radio
            class="select-item"
            v-for="(item, index) in questionVO.options"
            :value="`${String.fromCharCode('A'.charCodeAt(0) + index)}`"
            ><span class="select-item-text">{{ item }}</span></el-radio
          >
        </el-radio-group>

        <el-checkbox-group
          v-model="userAns"
          v-else-if="questionVO.type === 'multi_answer'"
        >
          <el-checkbox
            class="select-item"
            v-for="(item, index) in questionVO.options"
            :value="`${String.fromCharCode('A'.charCodeAt(0) + index)}`"
          >
            {{ item }}
          </el-checkbox>
        </el-checkbox-group>

        <el-input
          v-model="userAns"
          v-else-if="questionVO.type === 'fill_in_the_blank'"
          clearable
          maxlength="100"
          :show-word-limit="true"
          placeholder="每个空用中文分号'；'隔开"
        />

        <el-input
          v-model="userAns"
          type="textarea"
          v-else-if="questionVO.type === 'question_answer'"
          clearable
          placeholder="请输入答案..."
          rows="12"
          resize="none"
        />

        <div v-else>
          {{ questionVO }}
        </div>

        <el-row justify="end">
          <el-button
            type="primary"
            @click="judgeUserAns"
            style="margin-top: 20px"
            >提交</el-button
          >
        </el-row>
      </el-form>

      <el-collapse>
        <el-collapse-item title="查看答案">
          <VueMarkdownIt
            :source="questionVO.answer ?? '无'"
            :options="markdownItOption"
            preset="commonmark"
          />
        </el-collapse-item>
        <el-collapse-item title="解释">
          <VueMarkdownIt
            :source="questionVO.explain ?? '无'"
            :options="markdownItOption"
            preset="commonmark"
          />
        </el-collapse-item>

        <el-collapse-item title="考点">
          <el-tag v-for="item in questionVO.knowledge" type="primary">
            {{ item }}
          </el-tag>
        </el-collapse-item>
      </el-collapse>

      <el-row justify="space-between" style="margin-top: 10px; width: 100%">
        <el-col :span="4">
          <el-row justify="center">
            <el-link
              v-if="questionVO.id !== 1"
              :href="`/detail/${(questionVO.id ?? 0) - 1}`"
              >上一题</el-link
            >
          </el-row>
        </el-col>
        <el-col :span="4">
          <el-row justify="center">
            <el-link :href="`/detail/${(questionVO.id ?? 0) + 1}`"
              >下一题</el-link
            >
          </el-row>
        </el-col>
      </el-row>
    </div>
    <div id="detail-right"></div>
  </div>
</template>

<script setup lang="ts">
import router from "@/router";
import { QuestionControllerService, QuestionVO } from "@/api-client";
import { VueMarkdownIt } from "@f3ve/vue-markdown-it";
import { ElMessage } from "element-plus";

const questionVO = ref<QuestionVO>({});
const markdownItOption = {
  html: true,
  linkify: true,
  breaks: true,
};

const isLoading = ref(false);
const userAns = ref<any>();
onMounted(() => {
  loadData();
});
const loadData = async () => {
  isLoading.value = true;
  const questionId = router.currentRoute.value.params.id;
  console.log(questionId);

  try {
    const res = await QuestionControllerService.questionVoById(
      questionId as any
    );
    if (res.code === 0) {
      questionVO.value = { ...res.data };
      console.log(res.data);
    } else {
      ElMessage({
        type: "error",
        message: res.message,
      });
    }
  } catch (e) {
    console.log(e);
  } finally {
    isLoading.value = false;
  }
};

const judgeUserAns = () => {
  if (questionVO.value.type === "question_answer") {
    ElMessage({
      type: "warning",
      message: "问答题请参考答案",
    });
    return;
  }
  if (userAns.value === questionVO.value.answer) {
    ElMessage({
      type: "success",
      message: "回答正确",
    });
  } else {
    ElMessage({
      type: "error",
      message: "回答错误",
    });
  }
};
</script>

<style scoped lang="less">
#detail {
  display: flex;
  justify-content: space-between;
}

#detail-left {
  width: 150px;
}

#detail-main {
  width: 1000px;
  padding: 0px 10px;
}

#detail-right {
  width: 150px;
}
.question-Markdown-Class {
  font-size: 18px;
  font-weight: 600;
}

.user-form {
  margin-top: 10px;
  padding: 10px;
}

.select-item-text {
  font-size: 20px;
  font-weight: 400;
}
</style>
