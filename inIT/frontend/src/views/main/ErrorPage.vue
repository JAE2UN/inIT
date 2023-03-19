<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px"> </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <!-- side-bar end -->
        <el-main>
          <div
            class="error-page"
            style="font-weight: 600; font-size: 1.5rem; height: auto"
          >
            페이지를 찾을 수 없습니다
          </div>
          <el-button
            @click="movePageTo('Login')"
            style="position: relative; margin-top: 50px"
            >메인으로</el-button
          >
        </el-main>
        <!-- main end -->
      </el-container>
      <!-- container end -->
    </el-container>
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const movePageTo = (pageName) => {
  router.push({ name: pageName });
};
</script>
