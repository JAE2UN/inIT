<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img
          src="@/assets/images/inIT.png"
          alt="logo"
          class="header-logo"
          @click="movePageTo('Home')"
        />
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <el-aside width="220px">
          <side-bar />
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box">
            <!-- inner: 하나의 큰 박스 -> 바꾸고 싶다면 .inner 해제 -->
            <div class="inner" style="padding: 50px">
              <el-scrollbar>
                <div class="reviewNote-title">오답노트</div>
                <div class="reviewNote-content" v-if="rList.length > 0">
                  <el-collapse>
                    <el-collapse-item
                      class="reviewNote-item-title"
                      v-for="r in rList"
                      :title="r.quest"
                    >
                      <el-form>
                        <el-form-item>
                          <el-input type="hidden" :value="r.id" />
                        </el-form-item>
                        <el-form-item>
                          <el-input
                            class="reviewNote-item-content mt-4"
                            v-model="r.ans"
                            placeholder=""
                            :rows="3"
                          >
                            <template #append>
                              <el-button
                                style="margin-right: 20px"
                                @click="updateRItem(r)"
                                >수정</el-button
                              >
                              <el-button @click="deleteRItem(r)"
                                >삭제</el-button
                              >
                            </template>
                          </el-input>
                        </el-form-item>
                      </el-form>
                    </el-collapse-item>
                  </el-collapse>
                </div>
                <div class="reviewNote-isEmpty" v-else>
                  작성된 오답노트가 없습니다
                  <div class="reviewNote-isEmpty-desc">
                    면접 연습을 진행하고 오답노트를 작성해보세요
                  </div>
                </div>
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
import SideBar from "@/views/main/SideBar.vue";
import { RouterView, useRouter } from "vue-router";
import { useStore } from "vuex";
import { onMounted, computed } from "vue";

const store = useStore();
const router = useRouter();
const movePageTo = (pageName) => {
  router.push({ name: pageName });
};

const rList = computed(() => store.getters.rList);
// const rList = [];
// const userId = computed(() => store.getters.userInfo).id;
const userId = JSON.parse(sessionStorage.getItem("User")).id;

onMounted(() => {
  store.dispatch("readRList");
});

const updateRItem = (item) => {
  // userId, rId, ans
  const updateRInfo = JSON.stringify({
    id: item.id,
    userId: userId,
    ans: item.ans,
  });
  store.dispatch("updateRList", updateRInfo);
};

const deleteRItem = (item) => {
  const params = JSON.stringify({
    id: item.id,
    userId: userId,
  });
  store.dispatch("deleteRList", params);
};
</script>

<style></style>
