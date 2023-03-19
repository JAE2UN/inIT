import http from "@/api/http.js";

const user = {
  state: {
    isLogin: false,
    userInfo: {
      email: "",
      grade: 0,
      id: 0,
      imgUrl: null,
      nick: "",
      statusMsg: "",
      temperature: 0,
    },
    qList: [],
  },
  getters: {
    isLogin: (state) => state.isLogin,
    userInfo: (state) => state.userInfo,
    qList: (state) => state.qList,
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
    },
    SET_QLIST: (state, qList) => {
      state.qList = qList;
    },
  },
  actions: {
    readLoginInfo({ commit }, user) {
      commit("SET_USER_INFO", user);
    },
    doLogin({ commit }, user) {
      commit("SET_IS_LOGIN", true);
      commit("SET_USER_INFO", user);
      sessionStorage.setItem("User", JSON.stringify(user));
      sessionStorage.setItem("UserNick", user.nick);
    },
    doLogout({ commit }) {
      commit("SET_IS_LOGIN", false);
      commit("SET_USER_INFO", null);
      commit("SET_QLIST", null);
      sessionStorage.clear();
    },
    updateUserInfo({ commit }, user) {
      // 유저정보 갱신했을때
      commit("SET_USER_INFO", user);
      sessionStorage.setItem("User", JSON.stringify(user));
      sessionStorage.setItem("UserNick", user.nick);
    },
    async readQList({ commit }) {
      const userId = JSON.parse(sessionStorage.getItem("User")).id;
      await http
        .get("/userQuestion/" + userId)
        .then(({ data }) => {
          console.log("질문리스트 불러오기 성공");
          console.log(data);
          commit("SET_QLIST", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default user;
