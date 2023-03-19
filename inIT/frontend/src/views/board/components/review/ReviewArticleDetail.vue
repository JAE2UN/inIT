<template>
  <div class="article-detail feedback-detail">
    <el-row>
      <el-col :span="8" class="detail-board"> 면접 후기 게시판 </el-col>
    </el-row>
    <el-row class="detail-author">
      <!-- if isAuthor -->
      <el-col :offset="20" :span="2" v-if="articleData.userId == userPk">
        <el-button round @click="linkToEdit">수정</el-button>
      </el-col>
      <el-col :span="2" v-if="articleData.userId == userPk">
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
          <span class="author">익명의 면접자</span>
          <el-divider direction="vertical" />
          <span class="date">{{ articleData.createTime }}</span>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <ul class="review-info">
            <li>
              <i class="fas fa-building"></i> 회사명: {{ articleData.company }}
            </li>
            <li>
              <i class="far fa-calendar-check"></i> 면접 일자:
              {{ !!articleData ? articleData.realDate : articleData.realDate }}
            </li>
            <li>
              <i class="fas fa-edit"></i> 면접 정보: {{ articleData.info }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="detail-content">
          <pre>
            {{ articleData.content }}
          </pre>
        </el-col>
      </el-row>
      <el-divider class="divider-line" />
    </div>
    <!-- 댓글 -->
    <div class="comments">
      <el-row class="comments-row">
        <comment-list :commentsData="commentsData" :id="id" />
        <comment-form />
      </el-row>
    </div>
  </div>
</template>

<script setup>
  import CommentList from "./CommentListForm.vue";
  import CommentForm from "./CommentForm.vue";
  import { computed } from "vue";
  import { useRoute, useRouter } from "vue-router";
  import { useStore } from "vuex";

  const store = useStore();
  const route = useRoute();
  const id = route.params.articlePk;

  const userPk = JSON.parse(sessionStorage.User).id;
  const fetchInfo = {
    articlePk: id,
    userPk: userPk,
  };

  const articleData = computed(() => store.getters.reviewArticle);
  const commentsData = computed(() => store.getters.reviewComments);
  console.log(articleData.value)

  store.dispatch("fetchReviewArticle", fetchInfo);

  const router = useRouter();
  const linkToEdit = (name) =>
    router.push({ name: "ReviewArticleEdit", params: { artickePk: id } });

  const deleteArticle = () => {
    if (confirm("정말 삭제하시겠습니까?")) {
      store.dispatch("deleteReviewArticle", fetchInfo);
    }
  };
</script>

<style></style>
