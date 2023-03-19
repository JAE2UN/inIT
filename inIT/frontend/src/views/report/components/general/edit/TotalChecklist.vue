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
          <el-col v-for="check in checklist">
              <el-row v-if="editType(check.type, i)" class="total-modal-item">
                <el-col :span="15">
                  {{ check.content }}
                </el-col>
                <el-col :span="9">
                    <el-radio-group v-model="check.scale" size="small">
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
  checklist: Array,
})

const TotalChecklistDialogVisible = ref(false)

const Category = {
  1: '태도',
  2: '내용',
  3: '기타'
}

const editType = (type, divide) => {
  return parseInt( type / 10 ) == divide
}
</script>

<style>
  
</style>