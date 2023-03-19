<template>
  <div class="comment-list">
    <el-row class="comment-num">
      <el-col>
        댓글 <span> {{ commentsData.length }}</span>개
      </el-col>
    </el-row>
    <!-- v-for -->
    <el-row v-for="comment in commentsData" class="comment">
      <el-col :span="22">
        <div class="comment-info">
          <span class="comment-grade">lv.{{ comment.userGrade }}</span>
          <span class="comment-author">{{ comment.userNick }}</span>
          <span class="comment-temp">{{ comment.userTemperature }}℃</span>
          <el-divider direction="vertical" />
          <span class="comment-date">{{ comment.createTime }}</span>
        </div>
        <div class="comment-content">
          {{ comment.content }}
        </div>
      </el-col>
      <!-- v-if catecory is Feedback & isAuthor -->
      <el-col :span="1" class="comment-recommend">
        <div v-if="!comment.pick" @click="commentPick(comment.id, comment.pick)"><i class="far fa-thumbs-up recommend"></i></div>
        <div v-if="comment.pick" @click="commentPick(comment.id, comment.pick)"><i class="fas fa-thumbs-up recommend"></i></div>
      </el-col>
      <!-- v-if isCommentAuthor -->
      <el-col :span="1" class="delete-btn">
        <el-button v-if="comment.userNick == userNick" text @click="deleteComment(comment.id)">삭제</el-button >
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

const deleteComment = (commentPk) => {
  const commentData = {
    commentPk: commentPk,
    userPk: userPk,
    articlePk: props.id,
  }
  store.dispatch('deleteFeedbackComment', commentData)
}

const commentPick = (commentPk, pick) => {
  const commentData = {
    commentPk: commentPk,
    userPk: userPk,
    articlePk: props.id,
  }
  if (pick) {
    store.dispatch('commentUnpick', commentData)
  } else {
    store.dispatch('commentPick', commentData)
  }
}

</script>

<style>
  
</style>