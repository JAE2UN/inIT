import { createStore } from "vuex";
import user from "./modules/user";
import board from "./modules/board";
import interview from "./modules/interview";
import reviewBoard from "./modules/reviewBoard";
import report from "./modules/report";
import reviewNote from "./modules/reviewNote";
import dashboard from "./modules/dashboard";
import createPersistedState from "vuex-persistedstate";

const store = createStore({
  modules: {
    user,
    board,
    interview,
    reviewBoard,
    report,
    reviewNote,
    dashboard,
  },
  plugins: [
    createPersistedState({
      storage: sessionStorage,
      paths: ["dashboard", "user", "reviewNote"],
    }),
  ],
});

export default store;
