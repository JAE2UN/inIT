import { createRouter, createWebHistory } from "vue-router";

import Introduction from "../views/introduction/Introduction.vue";
import ErrorPage from "../views/main/ErrorPage.vue";
import Login from "../views/login/Login.vue";
import SignUp from "../views/login/Signup.vue";
import Profile from "../views/profile/MyProfile.vue";
import EditQList from "../views/profile/EditQList.vue";

import Dashboard from "../views/dashboard/Dashboard.vue";
import ReviewNote from "../views/reviewnote/ReviewNote.vue";

import Board from "../views/board/Board.vue";

import GeneralArticleList from "../views/board/components/general/GeneralArticleList.vue";
import GeneralArticleDetail from "../views/board/components/general/GeneralArticleDetail.vue";
import GeneralArticleCreate from "../views/board/components/general/GeneralArticleCreate.vue";
import GeneralArticleEdit from "../views/board/components/general/GeneralArticleEdit.vue";

import InfoArticleList from "../views/board/components/info/InfoArticleList.vue";
import InfoArticleDetail from "../views/board/components/info/InfoArticleDetail.vue";
import InfoArticleCreate from "../views/board/components/info/InfoArticleCreate.vue";

import FeedbackArticleList from "../views/board/components/feedback/FeedbackArticleList.vue";
import FeedbackArticleDetail from "../views/board/components/feedback/FeedbackArticleDetail.vue";
import FeedbackArticleCreate from "../views/board/components/feedback/FeedbackArticleCreate.vue";
import FeedbackArticleEdit from "../views/board/components/feedback/FeedbackArticleEdit.vue";

import ReviewArticleList from "../views/board/components/review/ReviewArticleList.vue";
import ReviewArticleDetail from "../views/board/components/review/ReviewArticleDetail.vue";
import ReviewArticleCreate from "../views/board/components/review/ReviewArticleCreate.vue";
import ReviewArticleEdit from "../views/board/components/review/ReviewArticleEdit.vue";

import InterviewSettings from "../views/interview/InterviewSettings.vue";
import Interview from "../views/interview/Interview.vue";
import InterviewCs from "../views/interview/InterviewCs.vue";
import InterviewReviewGeneral from "../views/interview/InterviewReviewGeneral.vue";
import InterviewReviewCs from "../views/interview/InterviewReviewCs.vue";

import VideoTest from "../views/interview/VideoTest.vue";
import TestInterview from "../views/interview/TestInterview.vue";
import InterviewGeneral from "../views/interview/InterviewGeneral.vue";

import MyReportList from "../views/report/MyReportList.vue";
import MyReportGeneralDetail from "../views/report/components/general/MyReportGeneralDetail.vue";
import MyReportGeneralEdit from "../views/report/components/general/edit/MyReportGeneralEdit.vue";
import MyReportCsDetail from "../views/report/components/cs/MyReportCsDetail.vue";
import MyReportCsEdit from "../views/report/components/cs/MyReportCsEdit.vue";
import MyReportRealDetail from "../views/report/components/real/MyReportRealDetail.vue";
import MyReportRealCreate from "../views/report/components/real/MyReportRealCreate.vue";
import MyReportRealEdit from "../views/report/components/real/MyReportRealEdit.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // 404 page
    {
      path: "/:pathMatch(.*)*",
      redirect: "/error",
    },
    {
      name: "error",
      path: "/error",
      component: ErrorPage,
    },
    // introduction
    // {
    //   name: "Introduction",
    //   path: "",
    //   component: Introduction,
    // },
    // login
    {
      name: "Login",
      // path: "/login",
      path: "",
      component: Login,
    },
    // signup
    // {
    //   name: "SignUp",
    //   path: "/signup",
    //   component: SignUp,
    // },
    // profile
    {
      name: "Profile",
      path: "/profile",
      component: Profile,
    },
    {
      name: "EditQList",
      path: "/editQList",
      component: EditQList,
    },
    // Home (Dashboard)
    {
      name: "Home",
      path: "/home",
      component: Dashboard,
    },
    // ReviewNote
    {
      name: "ReviewNote",
      path: "/reviewNote",
      component: ReviewNote,
    },
    // general board
    {
      name: "BoardGeneral",
      path: "/board/general",
      component: Board,
      children: [
        {
          name: "GeneralArticleList",
          path: "",
          component: GeneralArticleList,
        },
        {
          name: "GeneralArticleCreate",
          path: "create",
          component: GeneralArticleCreate,
        },
        {
          name: "GeneralArticleDetail",
          path: ":articlePk",
          component: GeneralArticleDetail,
        },
        {
          name: "GeneralArticleEdit",
          path: ":articlePk/edit",
          component: GeneralArticleEdit,
        },
      ],
    },
    // info board
    {
      name: "BoardInfo",
      path: "/board/info",
      component: Board,
      children: [
        {
          name: "InfoArticleList",
          path: "",
          component: InfoArticleList,
        },
        {
          name: "InfoArticleCreate",
          path: "create",
          component: InfoArticleCreate,
        },
        {
          name: "InfoArticleDetail",
          path: ":articlePk",
          component: InfoArticleDetail,
        },
      ],
    },
    // review board
    {
      name: "BoardReview",
      path: "/board/review",
      component: Board,
      children: [
        {
          name: "ReviewArticleList",
          path: "",
          component: ReviewArticleList,
        },
        {
          name: "ReviewArticleCreate",
          path: "create",
          component: ReviewArticleCreate,
        },
        {
          name: "ReviewArticleDetail",
          path: ":articlePk",
          component: ReviewArticleDetail,
        },
        {
          name: "ReviewArticleEdit",
          path: ":articlePk/edit",
          component: ReviewArticleEdit,
        },
      ],
    },
    // feedback board
    {
      name: "BoardFeedback",
      path: "/board/feedback",
      component: Board,
      children: [
        {
          name: "FeedbackArticleList",
          path: "",
          component: FeedbackArticleList,
        },
        {
          name: "FeedbackArticleCreate",
          path: "create",
          component: FeedbackArticleCreate,
        },
        {
          name: "FeedbackArticleDetail",
          path: ":articlePk",
          component: FeedbackArticleDetail,
        },
        {
          name: "FeedbackArticleEdit",
          path: ":articlePk/edit",
          component: FeedbackArticleEdit,
        },
      ],
    },
    // interview
    {
      name: "InterviewSettings",
      path: "/interview/settings",
      component: InterviewSettings,
    },
    // {
    //   name: "InterviewGeneral",
    //   // :reportPk 변경하기
    //   path: "/interview/general/:reportPk",
    //   component: VideoTest,
    // },
    {
      name: "Interview",
      path: "/interview/:type/:reportPk",
      component: InterviewGeneral,
    },
    {
      name: "InterviewReviewGeneral",
      path: "/interview/review/general/:reportPk",
      component: InterviewReviewGeneral,
    },
    {
      name: "InterviewReviewCs",
      path: "/interview/review/cs/:reportPk",
      component: InterviewReviewCs,
    },
    // report
    {
      name: "MyReportList",
      path: "/report",
      component: MyReportList,
    },
    // general report
    {
      name: "MyReportGeneralDetail",
      path: "/report/general/:reportPk",
      component: MyReportGeneralDetail,
    },
    {
      name: "MyReportGeneralEdit",
      path: "/report/general/:reportPk/edit",
      component: MyReportGeneralEdit,
    },
    // cs report
    {
      name: "MyReportCsDetail",
      path: "/report/cs/:reportPk",
      component: MyReportCsDetail,
    },
    {
      name: "MyReportCsEdit",
      path: "/report/cs/:reportPk/edit",
      component: MyReportCsEdit,
    },
    // real report
    {
      name: "MyReportRealDetail",
      path: "/report/real/:reportPk",
      component: MyReportRealDetail,
    },
    {
      name: "MyReportRealCreate",
      path: "/report/real/create",
      component: MyReportRealCreate,
    },
    {
      name: "MyReportRealEdit",
      path: "/report/real/:reportPk/edit",
      component: MyReportRealEdit,
    },
  ],
});

export default router;
