<template>
  <el-skeleton v-if="isLoading" animated>
    <template #template>
      <el-skeleton-item variant="rect" style="height: 100vh; width: 100%" />
    </template>
  </el-skeleton>
  <div id="question-edit">
    <el-form label-width="auto" label-position="top">
      <el-form-item label="题目描述:">
        <MdEditor
          v-model="updateContent"
          :code-foldable="false"
          preview-theme="github"
        />
      </el-form-item>
      <el-form-item label="答案:">
        <MdEditor
          v-model="updateAns"
          :code-foldable="false"
          preview-theme="github"
        />
      </el-form-item>
      <el-form-item label="答案解释:">
        <MdEditor
          v-model="updateExplian"
          :code-foldable="false"
          preview-theme="github"
        />
      </el-form-item>

      <el-form-item label="更新密码:">
        <el-input
          v-model="password"
          clearable
          type="password"
          show-password
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>

      <el-row justify="end">
        <el-button type="primary" @click="updateDate">更新</el-button>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { QuestionControllerService, UpdateQuestion } from "@/api-client";
import { MdEditor } from "md-editor-v3";
import router from "@/router";
import { ElMessage } from "element-plus";
const editModel = ref<UpdateQuestion>();
const isLoading = ref(false);

const updateContent = ref("");
const updateExplian = ref("");
const updateAns = ref("");
const password = ref("");
onMounted(() => {
  loadData();
});
const loadData = async () => {
  isLoading.value = true;
  const questionId = router.currentRoute.value.params.id;

  try {
    const res = await QuestionControllerService.questionVoById(
      questionId as any
    );
    if (res.code === 0) {
      editModel.value = { ...res.data };
      updateContent.value = editModel.value.content ?? "";
      updateAns.value = editModel.value.answer ?? "";
      updateExplian.value = editModel.value.explain ?? "";
      console.log(editModel.value);
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

const updateDate = async () => {
  const questionId: any = router.currentRoute.value.params.id;
  try {
    const res = await QuestionControllerService.updateQuestion({
      id: questionId,
      content: updateContent.value,
      explain: updateExplian.value,
      answer: updateAns.value,
      password: password.value,
    });
    if (res.code === 0) {
      ElMessage({
        type: "success",
        message: res.data,
      });
    } else {
      ElMessage({
        type: "error",
        message: res.message,
      });
    }
  } catch (e) {
    ElMessage({
      type: "error",
      message: "" + e,
    });
  } finally {
    loadData();
  }
};
</script>

<style scoped lang="less"></style>
