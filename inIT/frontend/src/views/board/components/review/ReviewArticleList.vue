<template>
  <div class="board-list no-tab-board feedback-list">
    <div class="board-title">
      면접 후기 게시판
    </div>
    <div class="btn-box">
      <!-- <el-button class="create-btn" color="#2c2cff" round @click="linkToCreate">글 등록</el-button> -->
    </div>
    <div class="article-table">
      <el-table :data="tableData">
        <el-table-column prop="id" label="글번호" min-width="100" />
        <el-table-column prop="title" label="제목" min-width="400">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span class="row-title cl-ellipsis" @click="linkToArticle(scope.row.id)">
                {{ scope.row.title }}
              </span>
              <span class="row-comment-count">{{ scope.row.commentCnt }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="작성자" min-width="120">
          익명의 면접자
        </el-table-column>
        <el-table-column label="작성일자" min-width="100">
          <template #default="scope">
            {{ scope.row.createTime }}
            <!-- {{ scope.row.createTime.substring(2, 10) }} -->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination class="pagination-block" layout="prev, pager, next" :total="50" />
  </div>

</template>

<script setup>
import { computed, onMounted } from 'vue'
import { useStore } from 'vuex';
import { ElTable } from 'element-plus'
import { useRouter } from "vue-router";

const store = useStore()
const tableData = computed(() => store.getters.reviewArticles)

onMounted(() => {
  store.dispatch('fetchReviewArticles')
});

const router = useRouter();
const linkToArticle = (id) => {
  router.push({ name: "ReviewArticleDetail", params: { articlePk: id } });
};
const linkToCreate = () => {
  router.push({ name: "ReviewArticleCreate" });
};


</script>

<style>
</style>