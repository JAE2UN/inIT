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
          <div class="main-container container-box report-container">
            <div class="inner">
              <el-scrollbar class="inner-scroll">
                  <el-row class="interview-info">
                    <el-col :span="6" class="interview-title">
                      <!-- <div class="fixed">
                        {{ reportData.title }}
                      </div> -->
                      <div class="edited title-box">
                        <el-input
                          placeholder="면접일지 제목을 입력하세요."
                          v-model="reportData.title"
                          @change="switchIsEditing"
                          maxlength="10"
                          show-word-limit
                        >
                        </el-input>
                        <el-divider class="divider-line"/>
                      </div>
                    </el-col>
                    <el-col :span="4" class="report-date">
                      {{ reportData.createdAt }}
                    </el-col>
                    <el-col :span="14" class="interview-save">
                      <el-button class="edit-btn save-btn" @click="submitCs">저장</el-button>
                    </el-col>
                  </el-row>
                  <el-row class="cs-report">

                    <!-- left section -->
                    <el-col :span="12" class="cs-list-inner">
                      <el-row>
                        <el-col class="q-list-title title">
                          면접 질문
                        </el-col>
                      </el-row>
                      <el-row class="q-list-row">
                        <el-scrollbar>
                          <!-- <el-col>
                            총 N문제 중 <i class="fas fa-check-circle best-fa"></i>가 n개,
                            <i class="fas fa-exclamation-triangle not-bad-fa"></i>가 m개,
                            <i class="fas fa-times-circle worst-fa"></i>가 l개입니다.
                          </el-col> -->
                          <!-- stat progress bar -->
                          <el-col>
                            <el-row class="progress-row">
                              <el-col :span="11" v-for="(val, i) in type" class="progress-item">
                                <span class="progress-category" v-if="average[i] != -1">{{ val }}</span>
                                <span class="category-disabled" v-if="average[i] == -1">{{ val }}</span>
                                <el-progress
                                  v-if="average[i] != -1"
                                  :stroke-width="18"
                                  :text-inside="true"
                                  :percentage="average[i]"
                                  :color="customColorMethod"
                                />
                                <div v-if="average[i] == -1" class="uddata"></div>
                              </el-col>
                              <!-- all questions -->
                              <el-col :span="11" class="more-box">
                              </el-col>
                            </el-row>
                          </el-col>
                          <!-- report comment -->
                          <el-row class="report-comment">
                            <el-col :span="3" class="comment-title title">
                              메모
                            </el-col>
                            <el-col class="comment-content">
                              <div class="fixed">
                                {{ reportData.checklistRes.allCmt }}
                              </div>
                            </el-col>
                          </el-row>
                        </el-scrollbar>
                      </el-row>
                    </el-col>


                    <!-- right section -->
                    <el-col :span="12" class="cs-detail d-flex">
                      <!-- star rate -->
                      <el-row class="report-rate">
                        <el-col class="rate-inner">
                          <span class="rate-title">나의 별점</span>
                          <span class="report-star">
                            <el-rate
                              v-model="reportData.star"
                              :texts="['힘을 내요 😭😭', '조금만 더 😓😓', '괜찮았어요😉😉', '잘했어요 😁😁', '완전 최고! 🥰🥰']"
                              allow-half
                              show-text
                            />
                          </span>
                        </el-col>
                      </el-row>
                      <!-- report simple comment -->
                      <el-row class="report-simple-comment">
                        <el-col :span="3" class="simple-title title">
                          한줄평
                        </el-col>
                        <el-col>
                          <el-input
                            placeholder="한줄평을 입력하세요."
                            v-model="reportData.oneCmt"
                            maxlength="35"
                            show-word-limit
                          >
                          </el-input>
                          <el-divider class="divider-line"/>
                        </el-col>
                      </el-row>
                      <!-- report tags -->
                      <el-row class="report-tag">
                        <el-col class="tag-title title">
                          나만의 태그
                        </el-col>
                        <el-col class="tag-content">
                          <el-tag
                            v-for="tag in tagList"
                            :key="tag"
                            class="mx-1"
                            closable
                            :disable-transitions="false"
                            @close="handleClose(tag)"
                          >
                            {{ tag }}
                          </el-tag>
                          <el-input
                            v-if="inputVisible"
                            ref="InputRef"
                            v-model="inputValue"
                            class="ml-1 w-20"
                            size="small"
                            @keyup.enter="handleInputConfirm"
                          />
                          <el-button v-if="!inputVisible" class="button-new-tag ml-1" size="small" @click="showInput">
                            + New Tag
                          </el-button>
                        </el-col>
                      </el-row>
                    </el-col>
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
import CsReportMyQList from "@/views/report/components/cs/CsReportMyQList.vue"
import CsReportDetail from "@/views/report/components/cs/CsReportDetail.vue"

import { ref, computed } from 'vue'
import { useStore } from 'vuex';
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const store = useStore()
const route = useRoute()
const reportPk = route.params.reportPk
const reportData = computed(() => store.getters.report)
const average = computed(() => store.getters.average)

store.dispatch('fetchMyReportCs', reportPk)

const linkToDetail = () => router.push({ name: "MyReportCsDetail",  params: { reportPk: reportPk } })

// progress bar
const type = {
  21: "운영체제",
  22: "웹",
  23: "데이터베이스",
  24: "네트워크",
  25: "알고리즘",
  26: "언어",
  29: "기타",
}

const customColorMethod = (percentage) => {
  if (percentage < 25) {
    return '#f23030'
  }
  else if (percentage < 50) {
    return '#f5c127'
  }
  else if (percentage < 75) {
    return '#2abf54'
  }
  else {
    return '#1892de'
  }
}


// tags
const inputValue = ref('')
const inputVisible = ref(false)

const tagList = ref([])
tagList.value = reportData.value.tags?.substring(1).split("#")

const handleClose = (tag) => {
  tagList.value.splice(tagList.value.indexOf(tag), 1)
}

const showInput = () => {
  inputVisible.value = true
}

const handleInputConfirm = () => {
  if (inputValue.value) {
    tagList.value.push(inputValue.value)
    console.log(tagList.value)
  }
  inputVisible.value = false
  inputValue.value = ''
}

// submit
const submitCs = () => {
  let mergeTag = ''
  if (!!tagList.value) {
    tagList.value.forEach(tag => {
      mergeTag = mergeTag + "#" + tag 
    })
  }
  reportData.value.tags = mergeTag
  console.log(tagList.value)
  
  const submitData = {
    id: reportData.value.id,
    oneCmt: reportData.value.oneCmt,
    star: reportData.value.star,
    tags: reportData.value.tags,
    title: reportData.value.title,
    type: reportData.value.type,
    userId: reportData.value.userId
  }

  store.dispatch("editMyReportCs", submitData)
}


</script>

<style>
</style>