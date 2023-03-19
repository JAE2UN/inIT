<template>
  <div class="chart-title">면접 연습 기록</div>
  <div class="chart-content d-flex" v-if="cmt5 > 1">
    <LineChartDetail
      :chartData="chart"
      style="width: 30vh; height: 20vh; margin-top: 10px"
    ></LineChartDetail>
    <div class="chart-desc">
      <div class="chart-desc-container">
        <div class="chart-desc-bad">
          <el-row class="chart-desc-title"
            >직전 평균이 가장 낮은 영역 😥</el-row
          >
          <el-row class="chart-desc-text"
            >{{ cat[badCateIdx] }} : {{ minScore }} 점</el-row
          >
        </div>
        <div class="chart-desc-good">
          <el-row class="chart-desc-title"
            >직전 평균이 가장 높은 영역 😊</el-row
          >
          <el-row class="chart-desc-text"
            >{{ cat[goodCateIdx] }} : {{ maxScore }} 점</el-row
          >
        </div>
      </div>
    </div>
  </div>
  <div
    class="chart-desc"
    v-else
    style="font-weight: 700; font-size: 1.8rem; color: mediumblue"
  >
    연습을 통해 나만의 차트를 쌓아보세요 📈
  </div>
</template>

<script setup>
import LineChartDetail from "./LineChartDetail.vue";
import { onMounted, computed } from "vue";
import { useStore } from "vuex";

const store = useStore();

const cmt5 = computed(() => store.getters.CMT5);
// const cmt5 = 1;
const chart = computed(() => store.getters.CMChart);
const cmLast = computed(() => store.getters.CMLast).value;

let maxScore = 0;
let minScore = 5;
for (let i = 0; i < 3; i++) {
  if (maxScore < cmLast[i]) maxScore = cmLast[i];
  if (minScore > cmLast[i]) minScore = cmLast[i];
}
const badCateIdx = Object.values(cmLast).indexOf(minScore);
const goodCateIdx = Object.values(cmLast).indexOf(maxScore);

const cat = ["태도", "내용", "기타"];

onMounted(async () => {
  await store.dispatch("readCMT5");
});
</script>

<style></style>
