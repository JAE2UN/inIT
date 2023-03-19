<template>
  <div class="total-title" @click="movePageTo('ReviewNote')">오답 노트</div>
  <div
    class="total-content-true"
    v-if="totalCount > 0"
    @click="movePageTo('ReviewNote')"
  >
    작성된 오답노트 수 : {{ totalCount }}
  </div>
  <div class="total-content-false" v-else>
    오답노트를 작성하고 한번에 관리하세요
  </div>
</template>
<script setup>
import { useRouter } from "vue-router";
import { onMounted, computed } from "vue";
import { useStore } from "vuex";

const router = useRouter();
const movePageTo = (pageName) => {
  router.push({ name: pageName });
};
const store = useStore();

const totalCount = computed(() => store.getters.rList).value.length;

onMounted(async () => {
  await store.dispatch("readRList");
});
</script>
<style lang=""></style>
