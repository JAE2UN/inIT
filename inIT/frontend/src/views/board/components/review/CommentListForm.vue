<template>
  <div class="comment-list">
    <el-row class="comment-num">
      <el-col>
        댓글 <span> {{ commentsData ? commentsData.length : 0 }}</span>개
      </el-col>
    </el-row>
    <!-- v-for -->
    <el-row v-for="comment in commentsData" class="comment">
      <el-col :span="20">
        <div class="comment-info">
          <!-- <span class="comment-grade">lv.{{ comment.userGrade }}</span> -->
          <span class="comment-author">익명의 댓글러</span>
          <!-- <span class="comment-temp">{{ comment.userTemperature }}℃</span>
          <el-divider direction="vertical" />
          <span class="comment-date">{{ comment.createTime }}</span> -->
        </div>
        <div class="comment-content">
          {{ comment.content }}
        </div>
      </el-col>
      <!-- v-if isCommentAuthor -->
      <el-col :span="1" v-if="userPk == comment.userId">
        <el-popover
          placement="bottom" 
          :show-arrow="false" 
          trigger="click" 
          popper-class="option-popover"
        >
          <el-row >
            <el-col :span="24">
              <span class="delete-btn" text @click="deleteComment(comment.id)">삭제</span >
            </el-col>
          </el-row>
          <template #reference>
            <div class="option-btn" @click="visible = true"><i class="fas fa-ellipsis-v"></i></div>
          </template>
        </el-popover>      
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


const deleteComment = (commentPk) => {
  const commentData = {
    commentPk: commentPk,
    userPk: userPk,
    articlePk: props.id,
  }
  store.dispatch('deleteReviewComment', commentData)
}

</script>

<style>
  
</style>