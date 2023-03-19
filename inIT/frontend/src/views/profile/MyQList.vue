<template>
  <div class="inner-myqlist">
    <div class="myqlist-header d-flex">
      <div class="myqlist-header-title">나만의 질문 리스트</div>
      <!-- <div class="myqlist-header-timestamp" :timpstamp="timestamp">
        수정날짜 : {{ timestamp }}
      </div> -->
    </div>
    <el-collapse class="myqlist-main">
      <el-collapse-item class="myqlist-main-cate" title="인적성" name="1">
        <el-row class="myqlist-main-item d-flex" v-for="q in QList">
          <div
            class="myqlist-main-item-low"
            v-if="q.priority == 1 && q.type == 10"
          >
            {{ q.quest }}
          </div>
          <div
            class="myqlist-main-item-mid"
            v-else-if="q.priority == 2 && q.type == 10"
          >
            {{ q.quest }}
          </div>
          <div
            class="myqlist-main-item-high"
            v-else-if="q.priority == 3 && q.type == 10"
          >
            {{ q.quest }}
          </div>
        </el-row>
      </el-collapse-item>
      <el-collapse-item class="myqlist-main-cate" title="CS" name="2">
        <el-row class="myqlist-main-item" v-for="q in QList">
          <div
            class="myqlist-main-item-low"
            v-if="q.priority == 1 && parseInt(q.type / 10) == 2"
          >
            {{ q.quest }}
          </div>
          <div
            class="myqlist-main-item-mid"
            v-else-if="q.priority == 2 && parseInt(q.type / 10) == 2"
          >
            {{ q.quest }}
          </div>
          <div
            class="myqlist-main-item-high"
            v-else-if="q.priority == 3 && parseInt(q.type / 10) == 2"
          >
            {{ q.quest }}
          </div>
        </el-row>
      </el-collapse-item>
      <el-collapse-item class="myqlist-main-cate" title="프로젝트" name="3">
        <el-row class="myqlist-main-item" v-for="q in QList">
          <el-row
            class="myqlist-main-item-low"
            v-if="q.priority == 1 && q.type == 30"
          >
            <el-col>{{ q.quest }}</el-col>
          </el-row>
          <el-row
            class="myqlist-main-item-mid"
            v-else-if="q.priority == 2 && q.type == 30"
          >
            <el-col>{{ q.quest }}</el-col>
          </el-row>
          <el-row
            class="myqlist-main-item-high"
            v-else-if="q.priority == 3 && q.type == 30"
          >
            <el-col>{{ q.quest }}</el-col>
          </el-row>
        </el-row>
      </el-collapse-item>
    </el-collapse>
    <div class="myqlist-footer d-flex">
      <el-button
        class="myqlist-footer-btn"
        :icon="EditPen"
        size="small"
        v-on:click="$router.push({ name: 'EditQList' })"
      >
        수정하기
      </el-button>
    </div>
  </div>
</template>
<script setup>
import { onMounted, computed, ref } from "vue";
import { useStore } from "vuex";
import { EditPen } from "@element-plus/icons-vue";
import http from "../../api/http";

const store = useStore();
const userId = JSON.parse(sessionStorage.getItem("User")).id;
const QList = computed(() => store.getters.qList);

onMounted(() => {
  store.dispatch("readQList");
});
</script>

<style></style>
