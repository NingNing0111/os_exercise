<template>
  <div>
    <el-scrollbar max-height="100vh" id="my-scrollbar">
      <el-header class="header">
        <el-row
          align="middle"
          class="full-header header-box"
          justify="space-between"
        >
          <div class="heade-left">
            <div class="header-icon-box">
              <el-image :src="OS" class="header-icon" />
            </div>
            <div class="header-title-box">
              <span
                class="header-title"
                @click="
                  () => {
                    $router.push({ path: '/' });
                  }
                "
                >操作系统题库</span
              >
            </div>
          </div>

          <div class="header-right">
            <a :href="GITHUB_REP" class="header-item-title">项目地址</a>
            <a :href="GITHUB_AUTHOR" class="header-item-title">联系作者</a>
          </div>
        </el-row>
      </el-header>
      <el-main>
        <RouterView />
      </el-main>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import OS from "@/assets/OS.svg";
import { GITHUB_REP, GITHUB_AUTHOR } from "@/common";
</script>

<style scoped lang="less">
.header {
  height: 70px;
  width: 100%;
  padding: 5px 20px;
}

.header-box {
  border-bottom: 1px solid gray;
}

.heade-left {
  width: 280px;
  height: 100%;
  display: flex;
  align-items: center;
}

.header-icon-box {
  width: 40px;
  height: 100%;
  padding: 0px 10px;
  display: flex;
  align-items: center;
}

.header-icon {
  width: 40px;
  height: 40px;
}

.header-title-box {
  min-width: 100px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.header-title {
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
  font-weight: 400;
  font-size: 20px;
  cursor: pointer;
}

.header-right {
  width: 60%;
  height: 100%;
  display: flex;
  justify-content: end;
  align-items: center;
  padding: 0px 50px 0px 10px;
}

.header-item-title {
  font-family: "Franklin Gothic Medium", "Arial Narrow", Arial, sans-serif;
  font-weight: 400;
  font-size: 18px;
  padding-left: 10px;
  color: gray;
}
</style>
