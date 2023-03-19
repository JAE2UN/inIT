<template>
  <div class="article-create">
    <GeneralArticleForm :articleData="articleData" action="글 등록"/>
  </div>
</template>

<script setup>
import GeneralArticleForm from "./GeneralArticleForm.vue"
import { ref } from "vue";

const userPk = JSON.parse(sessionStorage.User).id

const articleData = ref({
  type: 1,
  userId: userPk,
  title: "",
  content: "",
})
</script>

<style></style>
