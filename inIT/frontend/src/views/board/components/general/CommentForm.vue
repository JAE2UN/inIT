<template>
  <!-- <div> -->
    <el-form :model="commentData" class="comment-form">
      <el-row>
        <el-col :span="22">
          <el-form-item class="comment-input">
            <el-input v-model="commentData.content" @keydown.enter.prevent="onSubmit"/>
          </el-form-item>
        </el-col>
        <el-col :span="2" class="comment-submit">
          <el-form-item>
            <el-button class="comment-submit" color="#2c2cff" round @click="onSubmit">등록</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>    
  <!-- </div> -->
</template>

<script setup>
import { ref } from "vue"
import { useRoute } from "vue-router";
import { useStore } from "vuex";

const store = useStore()
const route = useRoute()
const userPk = JSON.parse(sessionStorage.User).id

const commentData = ref({
  boardId: route.params.articlePk,
  content: "",
  userId: userPk,
});

const onSubmit = () => {
  if (commentData.content != "") {
    store.dispatch('createComment', commentData.value)
    commentData.value.content = ""
  }
}
</script>

<style>
  
</style>