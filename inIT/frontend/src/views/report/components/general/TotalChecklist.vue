<template>
  <el-col class="btn-container">
    <el-button class="more-btn" @click="TotalChecklistDialogVisible = true">
      전체보기
    </el-button>
  </el-col>
  <!-- My checklist Modal Dialog -->
  <el-dialog v-model="TotalChecklistDialogVisible" width="45%">
    <el-scrollbar>
      <div class="total-modal-container">
        <el-row class="modal-title">
          나만의 체크리스트
        </el-row>
        <!-- attitude -->
        <el-row v-for="i in 3">
          <el-col class="category">
            {{ Category[i] }}
          </el-col>
          <!-- <el-col v-for="(check, m) in checkQs">
              <el-row v-if="parseInt(m / 4) == n" class="total-modal-item">
                <el-col :span="15">
                  {{ check[1] }}
                </el-col>
                <el-col :span="9">
                    <el-radio-group v-model="check[2]" size="small">
                      <el-radio-button label="1" />
                      <el-radio-button label="2" />
                      <el-radio-button label="3" />
                      <el-radio-button label="4" />
                      <el-radio-button label="5" />
                    </el-radio-group>
                </el-col>
              </el-row>
          </el-col> -->
          <el-col v-for="(check, m) in checklist.checklistRes.answerRes">
            <el-row v-if="check.flag == 2 && parseInt(m / 4) == i" class="total-modal-item">
              <el-col :span="15">
                {{ check.quest }}
              </el-col>
              <el-col :span="9">
                  <el-radio-group v-model="check.evalScore" size="small">
                    <el-radio-button label="1" />
                    <el-radio-button label="2" />
                    <el-radio-button label="3" />
                    <el-radio-button label="4" />
                    <el-radio-button label="5" />
                  </el-radio-group>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </div>
    </el-scrollbar>
  </el-dialog>    

</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  checklist: Object,
})

const TotalChecklistDialogVisible = ref(false)

const Category = {
  1: '태도',
  2: '내용',
  3: '기타'
}

// console.log(props.checklist.checklistRes.answerRes)
// console.log("HEY")
// const checkQs = ref([])
// props.checklist.checklistRes.answerRes.forEach(check => {
//   console.log(check)
//     if (check.flag == 2) {
//       checkQs.value.push([check.questNo, check.quest, check.evalScore])
//     }
// });
</script>

<style>
  
</style>