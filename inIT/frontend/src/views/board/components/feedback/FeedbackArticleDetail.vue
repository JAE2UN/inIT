<template>
  <div class="article-detail feedback-detail">
    <el-row>
      <el-col :span="8" class="detail-board">
        피드백 게시판
      </el-col>
    </el-row>
    <el-row class="detail-author">
      <!-- if isAuthor -->
      <el-col :offset="20" :span="2" v-if="articleData.userNick == userNick">
        <el-button round @click="linkToEdit">수정</el-button>
      </el-col>
      <el-col :span="2" v-if="articleData.userNick == userNick">
        <el-button round @click="deleteArticle">삭제</el-button>
      </el-col>
    </el-row>
    <!-- 제목, 작성자, 작성일자, 내용, 좋아요 -->
    <div class="content">
      <el-row>
        <el-col class="detail-title">
          {{ articleData.title }}
        </el-col>
      </el-row>
      <el-row>
        <el-col class="content-info">
          <span class="author">{{ articleData.userNick }}</span>
          <span class="grade">lv.{{ articleData.userGrade }}</span>
          <el-divider direction="vertical" />
          <span class="date">{{ articleData.createTime }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <div >
            <video class="reviewVideo" controls>
              <source src="https://interview.ml:4443/openvidu/recordings/6a4531112/6a4531112.mp4" type="video/mp4">
              해당 브라우저는 video 태그를 지원하지 않습니다.
            </video>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="detail-content">
          <pre>
            {{ articleData.content }}
          </pre>
        </el-col>
      </el-row>
      <el-divider class="divider-line"/>
    </div>
    <!-- 댓글 -->
    <div class="comments">
      <el-row class="comments-row">
        <feedback-comment-list :commentsData="commentsData" :id="id"/>
        <feedback-comment-form/>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import FeedbackCommentList from "./FeedbackCommentList.vue"
import FeedbackCommentForm from "./FeedbackCommentForm.vue"
import { ref, computed } from 'vue'
import { useRoute, useRouter } from "vue-router"
import { useStore } from 'vuex';

const store = useStore()
const route = useRoute()
const id = route.params.articlePk

const articleData = computed(() => store.getters.article)
const commentsData = computed(() => store.getters.comments)
// const createdAt = articleData.value.createTime.substring(2, 10)

store.dispatch('fetchFeedbackArticle', id)

const userPk = JSON.parse(sessionStorage.User).id
const userNick = JSON.parse(sessionStorage.User).nick

const fetchInfo = {
  articlePk: id,
  userPk: userPk,
}

const router = useRouter();
const linkToEdit = (name) => router.push({ name: "FeedbackArticleEdit", params: { artickePk: id } });

const deleteArticle = () => {
  if (confirm("정말 삭제하시겠습니까?")) {
    store.dispatch("deleteFeedbackArticle", fetchInfo)
  }
}


</script>

<style>
</style>