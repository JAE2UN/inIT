import http from "@/api/http.js";
import router from "../../router";

const reviewNote = {
  state: {
    // 오답노트 질문/메모 리스트
    rList: [],
  },
  getters: {
    rList: (state) => state.rList,
  },
  mutations: {
    SET_RLIST: (state, rList) => {
      state.rList = rList;
    },
    RESET_RLIST: (state) => {
      state.rList = [];
    },
  },
  actions: {
    async readRList({ commit }) {
      commit("RESET_RLIST");
      const userId = JSON.parse(sessionStorage.getItem("User")).id;
      await http
        .get("/scrap/" + userId)
        .then(({ data }) => {
          commit("SET_RLIST", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    async updateRList({}, updateRInfo) {
      await http
        .post("/scrap/update", updateRInfo)
        .then(({ data }) => {
          router.go();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteRList({}, params) {
      const id = JSON.parse(params).id;
      const userId = JSON.parse(params).userId;
      http
        .delete("/scrap/" + userId + "/" + id)
        .then(({ data }) => {
          if (data == 1) {
            router.go();
          } else if (data == -1) {
            console.log("삭제 권한이 없습니다");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default reviewNote;
