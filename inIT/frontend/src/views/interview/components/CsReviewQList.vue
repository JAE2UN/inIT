<template>
  <el-col :span="12" class="review-right">
    <div class="right-container d-flex">

      <!-- my checklist -->
      <el-row class="checklist-title">
        기술면접 질문
      </el-row>
      <el-scrollbar>
        <div v-for="i in 3">
          <el-row class="category">{{ Category[i] }}</el-row>
          <div v-for="question in questions">
            <el-row class="checklist" v-if="question.type == i">
              <el-col :span="16" class="check-content">
                <span>
                  {{ question.question }}
                </span>
              </el-col>
              <el-col :span="8" class="rate">
                <span class="rate">
                  <el-radio-group v-model="question.scale" size="small">
                    <el-radio-button class="correct" label="○" />
                    <el-radio-button class="not-bad" label="△" />
                    <el-radio-button class="wrong" label="×"/>
                  </el-radio-group>
                </span>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-scrollbar>
      </div>
  </el-col>
</template>

<script setup>
const props = defineProps({
  questions: Array,
})

const Category = {
  1: '컴퓨터 구조',
  2: '네트워크',
  3: 'DB'
}


</script>

<style>  
</style>