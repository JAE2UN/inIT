<template>
  <div class="chart-title">CS 연습 기록</div>
  <div class="chart-content" v-if="cst5 > 0">
    <PieChartDetail
      style="width: 30vh; height: 20vh; margin-top: 10px"
      :chartData="chart"
    ></PieChartDetail>
    <div class="chart-desc">
      <div class="chart-desc-container">
        <div class="chart-desc-bad">
          <el-row class="chart-desc-title">가장 오답이 많은 영역 😥</el-row>
          <el-row class="chart-desc-text"
            >{{ cat[badCateIdx] }} : {{ maxBadScore }}개</el-row
          >
        </div>
        <div class="chart-desc-good">
          <el-row class="chart-desc-title">가장 정답이 많은 영역 😊</el-row>
          <el-row class="chart-desc-text"
            >{{ cat[goodCateIdx] }} : {{ maxGoodScore }}개</el-row
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
    연습을 통해 부족한 부분을 체크해보세요 📋
  </div>
</template>
<script setup>
import PieChartDetail from "./PieChartDetail.vue";
import { onMounted, computed } from "vue";
import { useStore } from "vuex";

const store = useStore();

const cst5 = computed(() => store.getters.CST5);
// const cst5 = 0;
const chart = computed(() => store.getters.CSChart);
const cmBSG = computed(() => store.getters.CSBSG).value;

let maxBadScore = 0;
let maxGoodScore = 0;
for (let i = 0; i < 7; i++) {
  if (maxBadScore < cmBSG.bad[i]) maxBadScore = cmBSG.bad[i];
  if (maxGoodScore < cmBSG.good[i]) maxGoodScore = cmBSG.good[i];
}

const badCateIdx = Object.values(cmBSG.bad).indexOf(maxBadScore);
const goodCateIdx = Object.values(cmBSG.good).indexOf(maxGoodScore);

const cat = [
  "기타",
  "운영체제",
  "웹",
  "데이터베이스",
  "네트워크",
  "알고리즘",
  "언어",
];

onMounted(async () => {
  await store.dispatch("readCST5");
});
</script>
