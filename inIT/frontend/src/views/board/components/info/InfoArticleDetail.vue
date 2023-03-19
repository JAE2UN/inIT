<template>
  <div class="article-detail info-detail">
    <el-row>
      <!-- v-if 자유/질문/정보 -->
      <el-col :span="8" class="detail-board"> 정보게시판 </el-col>
    </el-row>
    <el-row class="detail-author">
      <!-- if isAuthor -->
      <!-- <el-col :offset="20" :span="2">
        <el-button round>수정</el-button>
      </el-col>
      <el-col :span="2">
        <el-button round>삭제</el-button>
      </el-col> -->
    </el-row>
    <!-- 제목, 작성자, 작성일자, 내용, 좋아요 -->
    <div class="content">
      <el-row>
        <el-col class="detail-title"> 제목입니당 {{ id }} </el-col>
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
        <el-col class="detail-content">
          <pre>
            {{ articleData.content }}
          </pre>
        </el-col>
      </el-row>
      <el-row class="article-like">
        <el-col>
          <el-button> 좋아요 {{ articleData.likesCnt }} </el-button>
        </el-col>
      </el-row>
      <el-divider class="divider-line" />
    </div>
    <!-- 댓글 -->
    <div class="comments">
      정보게시판에서는 댓글을 달 거나 볼 수 없습니다.
      <!-- <el-row class="comments-row">
        <comment-list-form
          :comments="articleData.comments"
          :type="articleData.type"
        ></comment-list-form>
      </el-row>
      <comment-form></comment-form> -->
    </div>
  </div>
</template>

<script setup>
// import CommentListForm from "./CommentListForm.vue";
// import CommentForm from "./CommentForm.vue";
import { onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

const store = useStore();
const route = useRoute();

const id = route.params.articlePk
const userPk = JSON.parse(sessionStorage.User).id
const articleData = computed(() => store.getters.article);
// const createdAt = articleData.value.createTime.substring(2, 10)

const fetchInfo = {
  articlePk: id,
  userPk: userPk,
}

onMounted(() => {
  store.dispatch('fetchArticle', fetchInfo)
});

</script>

<style></style>
