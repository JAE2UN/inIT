<template>
  <div class="article-create">
    <feedback-article-form :articleData="articleData" action="글 등록"/>
  </div>
</template>

<script setup>
import FeedbackArticleForm from "./FeedbackArticleForm.vue"
import { ref } from "vue";

const userPk = JSON.parse(sessionStorage.User).id

const articleData = ref({
  type: 1,
  userId: userPk,
  title: "",
  content: "",
  reportId: 46,
  sequence: 4,
})
</script>

<style>
</style>