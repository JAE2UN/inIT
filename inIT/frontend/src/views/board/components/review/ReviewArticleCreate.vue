<template>
  <div class="article-create review-article-create">
    <review-article-form :articleData="articleData" action="글 등록"/>
  </div>
</template>

<script setup>
import ReviewArticleForm from "./ReviewArticleForm.vue"
import { ref } from "vue";
import { useRoute } from "vue-router"

const route = useRoute()

const userPk = JSON.parse(sessionStorage.User).id
const reportPk = route.params.reportPk

const articleData = ref({
  realreportId: reportPk,
  userId: userPk,
  title: "",
  content: "",
})
</script>

<style>
</style>