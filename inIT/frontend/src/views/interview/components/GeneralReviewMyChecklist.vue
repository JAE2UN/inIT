<template>
  <el-col :span="12" class="review-right">
    <div class="right-container d-flex">
      <!-- my checklist -->
      <el-row class="checklist-title"> 나만의 체크리스트 </el-row>
      <div v-for="i in 3">
        <el-row class="category">{{ Category[i] }}</el-row>
        <div v-for="question in questionList">
          <el-row
            class="checklist"
            v-if="
              question.flag == 2 && Math.floor((question.questNo + 3) / 4) == i
            "
          >
            <el-col :span="16" class="check-content">
              <span>
                {{ question.quest }}
              </span>
            </el-col>
            <el-col :span="8" class="rate">
              <span class="rate">
                <el-radio-group v-model="question.evalScore" size="small">
                  <el-radio-button label="1" />
                  <el-radio-button label="2" />
                  <el-radio-button label="3" />
                  <el-radio-button label="4" />
                  <el-radio-button label="5" />
                </el-radio-group>
              </span>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </el-col>
</template>

<script setup>
  import { computed } from "vue";
  import { useStore } from "vuex";

  const store = useStore();

  const Category = {
    1: "태도",
    2: "내용",
    3: "기타",
  };

  const questionList = computed(() => store.getters.questionList);
</script>

<style></style>
