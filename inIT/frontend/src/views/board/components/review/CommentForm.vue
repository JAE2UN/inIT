<template>
  <div>
    <el-form :model="commentData" class="comment-form">
      <el-row :gutter="20">
        <el-col :span="21">
          <el-form-item class="comment-input">
            <el-input v-model="commentData.content" @keydown.enter.prevent="onSubmit"/>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="2">
          <el-form-item>
            <el-button class="comment-submit" color="#2c2cff" round @click="onSubmit">등록</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>    
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRoute } from "vue-router";
import { useStore } from "vuex";

const store = useStore()
const route = useRoute()
const userPk = JSON.parse(sessionStorage.User).id

const commentData = ref({
  reviewId: route.params.articlePk,
  content: "",
  userId: userPk,
});

const onSubmit = () => {
  if (commentData.value.content != "") {
  store.dispatch('createReviewComment', commentData.value)
  commentData.value.content = ""
  }
}
</script>

<style>
  
</style>