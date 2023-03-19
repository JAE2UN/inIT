<template>
  <div class="total-title" @click="movePageTo('BoardFeedback')">
    면접 일지 태그
  </div>
  <div class="total-content-true" v-if="true">
    <div class="tag-list">
      <el-row @click="movePageTo('MyReportList')">삼성 SDI</el-row>
      <el-row @click="movePageTo('MyReportList')">멀티캠퍼스</el-row>
      <el-row></el-row>
    </div>
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const movePageTo = (pageName) => {
  router.push({ name: pageName });
};
</script>
