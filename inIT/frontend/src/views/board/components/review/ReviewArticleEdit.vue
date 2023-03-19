<template>
  <div class="article-create">
    <GeneralArticleForm :articleData="articleData" action="글 수정"/>
  </div>
</template>

<script setup>
import GeneralArticleForm from "./ReviewArticleForm.vue"
import { computed } from "vue";
import { useRoute } from "vue-router"
import { useStore } from "vuex";


const route = useRoute()
const id = route.params.articlePk

const store = useStore()
const articleData = computed(() => store.getters.reviewArticle)

store.dispatch('fetchArticle', id)

</script>

<style></style>
