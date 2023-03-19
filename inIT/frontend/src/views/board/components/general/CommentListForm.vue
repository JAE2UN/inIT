<template>
  <div class="comment-list">
    <el-row class="comment-num">
      <el-col>
        댓글 <span> {{ commentsData.length }}</span>개
      </el-col>
    </el-row>
    <!-- v-for -->
    <el-row v-for="comment in commentsData" class="comment">
      <el-col :span="20">
        <div class="comment-info">
          <span class="comment-grade">lv.{{ comment.userGrade }}</span>
          <span class="comment-author">{{ comment.userNick }}</span>
          <span class="comment-temp">{{ comment.userTemperature }}℃</span>
          <el-divider direction="vertical" />
          <span class="comment-date">{{ comment.createTime }}</span>
          <!-- <span class="comment-date">{{ comment.createTime.substring(2, 10) }}</span> -->
        </div>
        <div class="comment-content">
          {{ comment.content }}
        </div>
      </el-col>
      <!-- v-if isCommentAuthor -->
      <el-col :span="4" v-if="comment.userNick == userNick" class="delete-btn">
        <el-button text @click="deleteComment(comment.id)">삭제</el-button >
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import { useStore } from "vuex";

const store = useStore()

const props = defineProps({
  commentsData: Object,
  id: String,
})

const userPk = JSON.parse(sessionStorage.User).id
const userNick = JSON.parse(sessionStorage.User).nick

// delete comment
const deleteComment = (commentPk) => {
  const commentData = {
    commentPk: commentPk,
    userPk: userPk,
    articlePk: props.id,
  }
  store.dispatch('deleteComment', commentData)
}

</script>

<style>
  
</style>