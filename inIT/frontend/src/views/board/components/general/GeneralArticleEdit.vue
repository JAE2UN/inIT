<template>
  <div class="article-create">
    <GeneralArticleForm :articleData="articleData" action="글 수정"/>
  </div>
</template>

<script setup>
import GeneralArticleForm from "./GeneralArticleForm.vue"
import { computed } from "vue";
import { useRoute } from "vue-router"
import { useStore } from "vuex";


const route = useRoute()
const id = route.params.articlePk

const store = useStore()
const articleData = computed(() => store.getters.article)

store.dispatch('fetchArticle', id)

</script>

<style></style>
