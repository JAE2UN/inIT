<template>
  <div class="article-create">
    <feedback-article-form :articleData="articleData" action="글 수정"/>
  </div>
</template>

<script setup>
import FeedbackArticleForm from "./FeedbackArticleForm.vue"
import { computed } from "vue";
import { useRoute } from "vue-router"
import { useStore } from "vuex";


const route = useRoute()
const id = route.params.articlePk

const store = useStore()
const articleData = computed(() => store.getters.article)
console.log("edit", articleData.value)
store.dispatch('fetchFeedbackArticle', id)

</script>

<style></style>
