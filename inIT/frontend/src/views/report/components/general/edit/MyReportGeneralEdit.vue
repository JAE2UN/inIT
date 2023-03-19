<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img src="@/assets/images/inIT.png" alt="logo" class="header-logo">
      </el-header>
      <!-- header end -->
      <el-container class="entire-container report">
        <el-aside width="220px">
          <side-bar/>
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box cs-report-container">
            <div class="inner">
              <el-scrollbar class="inner-scroll">
                  <!-- title, date -->
                  <el-row class="interview-info">
                    <el-col :span="6" class="interview-title">
                      <!-- <div v-if="!isEditing" class="fixed">
                        {{ reportData.title }}
                      </div> -->
                      <div class="edited title-box">
                        <el-input
                          placeholder="면접일지 제목을 입력하세요."
                          v-model="reportData.title"
                          maxlength="10"
                          show-word-limit
                        >
                        </el-input>
                        <el-divider class="divider-line"/>
                      </div>
                    </el-col>
                    <!-- <el-col :span="1" class="interview-title-lh">
                      <span class="edit-btn" @click="switchIsEditing">
                        <i class="fas fa-pen"></i>
                      </span>
                    </el-col> -->
                    <el-col :span="4" class="interview-date interview-title-lh">
                      {{ reportData.createdAt }}
                      <!-- {{ reportData.createdAt.substring(2, 10) }} -->
                    </el-col>
                    <el-col :span="14" class="interview-save">
                      <el-button class="edit-btn save-btn" v-if="!isEditing" @click="submitGeneral">저장</el-button>
                      <el-button class="back-btn" v-if="!isEditing" @click="linkTo('MyReportList')">목록으로</el-button>
                    </el-col>
                  </el-row>
                  <el-row class="general-report-inner">
                    <GeneralReportDetail :checklistRes="reportData.checklistRes"/>
                    <GeneralReportMyChecklist :average="average" :reportData="reportData" @getTag="(data) => tagList = data"/>
                  </el-row>
              </el-scrollbar>
            </div>
          </div>
        </el-main>
        <!-- main end -->
      </el-container>
      <!-- container end -->
    </el-container>
  </div>
</template>

<script setup>
import SideBar from "@/views/main/SideBar.vue"
import GeneralReportDetail from "@/views/report/components/general/edit/GeneralReportDetail.vue"
import GeneralReportMyChecklist from "@/views/report/components/general/edit/GeneralReportMyChecklist.vue"
import { ref, onMounted, computed } from 'vue'
import { useStore } from 'vuex';
import { useRouter, useRoute } from "vue-router";

const router = useRouter()
const store = useStore()
const route = useRoute()
const reportPk = route.params.reportPk
const reportData = computed(() => store.getters.report)
const average = computed(() => store.getters.average)

onMounted(() => {
  store.dispatch('fetchMyReport', reportPk)
});

const linkTo = (name) => router.push({ name: name });

// tag
const tagList = ref([])
tagList.value = reportData.value.tags?.substring(1).split("#")

const submitGeneral = () => {
  let mergeTag = ''
  if (tagList.value != "") {
    tagList.value.forEach(tag => {
      mergeTag = mergeTag + "#" + tag 
    })
  }
  reportData.value.tags = mergeTag
  
  const submitData = {
    id: reportData.value.id,
    oneCmt: reportData.value.oneCmt,
    star: reportData.value.star,
    tags: reportData.value.tags,
    title: reportData.value.title,
    type: reportData.value.type,
    userId: reportData.value.userId
  }

  store.dispatch("editMyReport", submitData)
}


</script>

<style>
</style>