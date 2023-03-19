<template>
  <div class="article-detail" v-if="isLoad">
    <el-row>
      <!-- v-if 자유/질문/정보 -->
      <el-col :span="8" class="detail-board">
        자유게시판 - <span>{{ Category[articleData.type] }}</span>
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
          <!-- <span class="date">{{ articleData.createTime.substring(2, 10) }}</span> -->
        </el-col>
      </el-row>
      <el-row>
        <el-col class="detail-content">
          <pre>
            {{ articleData.content }}
          </pre>
        </el-col>
      </el-row>
      <el-row class="article-like">
        <el-col>
          <el-button @click="articleLike" v-if="articleData.userAlreadyPush" color="#2c2cff">
            좋아요 {{ articleData.likesCnt }}
          </el-button>
          <el-button @click="articleLike" v-else>
            좋아요 {{ articleData.likesCnt }}
          </el-button>
        </el-col>
      </el-row>
      <el-divider class="divider-line"/>
    </div>
    <!-- 댓글 -->
    <div class="comments">
      <el-row class="comments-row">
        <comment-list-form :commentsData="commentsData" :id="id"></comment-list-form>
      </el-row>
      <el-row>
        <comment-form></comment-form>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import CommentListForm from "./CommentListForm.vue"
import CommentForm from "./CommentForm.vue"
import { ref, computed, onMounted } from "vue"
import { useRoute, useRouter } from "vue-router"
import { useStore } from "vuex";


const Category = ref({
  1: "일반",
  2: "질문",
  3: "정보",
})
 

// axios
const route = useRoute()
const id = route.params.articlePk
const router = useRouter()
const linkToEdit = (name) => router.push({ name: "GeneralArticleEdit", params: { artickePk: id } });

const store = useStore()
const articleData = computed(() => store.getters.article)
const commentsData = computed(() => store.getters.comments)
const isLoad = computed(() => store.getters.isLoad)

const userPk = JSON.parse(sessionStorage.User).id
const userNick = JSON.parse(sessionStorage.User).nick
const fetchInfo = {
  articlePk: id,
  userPk: userPk,
}

onMounted(() => {
  store.dispatch('fetchArticle', fetchInfo)
});

const deleteArticle = () => {
  if (confirm("정말 삭제하시겠습니까?")) {
    store.dispatch("deleteArticle", fetchInfo)
  }
}

const articleLike = () => {
  if (articleData.value.userAlreadyPush) {
    store.dispatch("articleUnlike", fetchInfo)
  } else {
    store.dispatch("articleLike", fetchInfo)
  }
}

</script>

<style>
</style>