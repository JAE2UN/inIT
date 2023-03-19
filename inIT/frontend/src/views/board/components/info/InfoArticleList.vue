<template>
  <div class="board-list no-tab-board">
    <div class="board-title">정보 게시판</div>
    <div class="btn-box info-btn-box">
      <!-- 로그인한 유저가 Admin 인 경우에만 글 등록버튼 표시 -->
      <el-button class="create-btn" color="#2c2cff" round v-show="isAdmin"
        >글 등록</el-button
      >
    </div>
    <div class="article-table">
      <el-table :data="tableData" class="no-tabs">
        <el-table-column prop="id" label="글번호" min-width="100" />
        <el-table-column prop="title" label="제목" min-width="400">
          <template #default="scope">
            <div style="display: flex; align-items: center">
              <span
                class="row-title cl-ellipsis"
                @click="linkToArticle(scope.row.id)"
              >
                {{ scope.row.title }}
              </span>
              <!-- <span class="row-comment-count">{{ scope.row.commentCnt }}</span> -->
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="userNick" label="작성자" min-width="120" />
        <el-table-column label="작성일자" min-width="100">
          <template #default="scope">
            {{ scope.row.createTime }}
            <!-- {{ scope.row.createTime.substring(2, 10) }} -->
          </template>
        </el-table-column>
        <el-table-column label="분류" min-width="100">
          <template #default="scope">
            {{ Category[scope.row.type] }}
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination
      class="pagination-block"
      layout="prev, pager, next"
      :total="totalElements"
      @current-change="changePage"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { ElTable } from "element-plus";
import { useRouter } from "vue-router";

const Category = {
  1: "일반",
  2: "질문",
  3: "정보",
};

const activeName = ref(0);

// axios
const store = useStore();
const tableData = computed(() => store.getters.articles);
const totalElements = computed(() => store.getters.totalElements);

onMounted(() => {
  store.dispatch("fetchInfoArticles", 0);
});

const changePage = (page) => {
  page -= 1;
  store.dispatch("fetchInfoArticles", page);
};

// router push
const router = useRouter();
const linkToArticle = (id) => {
  router.push({ name: "InfoArticleDetail", params: { articlePk: id } });
};
</script>

<style></style>
