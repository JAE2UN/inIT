<template>
  <div class="board-list">
    <!-- v-if 게시판=자유/질문/정보 -->
    <div class="board-title">자유 게시판</div>
    <div class="btn-box">
      <el-button class="create-btn" color="#2c2cff" round @click="linkToCreate"
        >글 등록</el-button
      >
    </div>
    <el-tabs v-model="activeName" @tab-change="changeCategory">
      <el-tab-pane v-for="tab in Category" :label="tab.label" :name="tab.type">
        <div class="article-table">
          <el-table :data="tableData">
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
                  <span class="row-comment-count">{{
                    scope.row.commentCnt
                  }}</span>
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
            <el-table-column prop="likesCnt" label="추천" min-width="60" />
          </el-table>
        </div>
      </el-tab-pane>
    </el-tabs>
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

const Category = [
  {
    type: 0,
    label: "전체",
  },
  {
    type: 1,
    label: "일반",
  },
  {
    type: 2,
    label: "질문",
  },
  {
    type: 3,
    label: "정보",
  },
];

const activeName = ref(0);

// axios
const store = useStore();
const tableData = computed(() => store.getters.articles);
const totalElements = computed(() => store.getters.totalElements);

onMounted(() => {
  store.dispatch("fetchArticles", 0);
});

const changeCategory = () => {
  // console.log("category : " + activeName.value);
  if (activeName.value) {
    const params = JSON.stringify({
      type: activeName.value,
      page: 0,
    });
    store.dispatch("fetchTypeArticles", params);
    // store.dispatch("fetchTypeArticles", activeName.value);
  } else {
    store.dispatch("fetchArticles", 0);
    // store.dispatch("fetchArticles");
  }
};

const changePage = (page) => {
  page -= 1;
  if (activeName.value) {
    const params = JSON.stringify({
      type: activeName.value,
      page: page,
    });
    store.dispatch("fetchTypeArticles", params);
  } else {
    console.log(false);
    // console.log("onchanged page : " + page);
    store.dispatch("fetchArticles", page);
  }
};

// router push
const router = useRouter();
const linkToArticle = (id) => {
  router.push({ name: "GeneralArticleDetail", params: { articlePk: id } });
};
const linkToCreate = () => {
  router.push({ name: "GeneralArticleCreate" });
};
</script>

<style></style>
