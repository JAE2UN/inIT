<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img src="@/assets/images/inIT.png" alt="logo" class="header-logo" />
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <!-- <el-aside width="220px">
          <side-bar/>
        </el-aside> -->
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box interview-room">
            <!-- interview view -->
            <div class="inner interview">
              <!-- v-if JoinSession -->
              <el-scrollbar class="inner-scroll">
                <el-row>
                  <!-- <div class="question-box" v-if="!recording && !start">
                    <div class="interview-question">
                      나오는 문제를 읽고 차분히 답하세요.
                    </div>
                  </div> -->
                  <div class="question-box" v-if="recording && start">
                    <div class="interview-question">
                      Q{{ question.questNo }}. {{ question.quest }}
                    </div>
                  </div>
                  <div class="question-box" v-if="!recording && start">
                    <div class="interview-question">
                      로딩중입니다
                    </div>
                  </div>
                </el-row>
                <el-row>
                  <div class="video-box">
                    <user-video :stream-manager="mainStreamManager" />
                    <!-- <stopwatch class="stopwatch" /> -->
                  </div>
                </el-row>
                <el-row class="interview-check">
                  <el-col class="btn-box" :span="14">
                    <el-button
                      class="next-btn"
                      v-if="!recording && start"
                      @click="pause"
                    >
                      다음 문항
                    </el-button>
                    <el-button
                      class="next-btn"
                      v-if="recording && start && !isFinished"
                      @click="stopInterview"
                    >
                      답변 완료
                    </el-button>
                    <el-button
                      class="next-btn"
                      v-if="recording && start && isFinished"
                      @click="exitDialogVisible = true"
                    >
                      연습 종료
                    </el-button>
                  </el-col>
									<el-col class="btn-box" :span="10">
                    <el-button
                      class="exit-btn"
                      @click="exitDialogVisible = true"
                    >
                      연습 종료
                    </el-button>
									</el-col>
                </el-row>
              </el-scrollbar>
            </div>
          </div>
          <!-- exit dialog -->
          <el-dialog v-model="exitDialogVisible" custom-class="exit-dialog">
            <el-row class="dialog-row">
              <el-col class="dialog-box"> 연습이 종료되었어요! </el-col>
              <el-col class="next-dialog">
                체크리스트를 통해 스스로 돌아보는 건 어때요?
              </el-col>
              <el-col class="btn-box">
                <el-button class="now-btn" @click="linkToEval"
                  >리뷰하러 가기</el-button
                >
              </el-col>
            </el-row>
          </el-dialog>
        </el-main>
        <!-- main end -->
      </el-container>
      <!-- container end -->
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions, mapGetters } from "vuex";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/views/interview/components/UserVideo.vue";
import Stopwatch from "./components/Stopwatch.vue";
import http from "@/api/http.js";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://interview.ml:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "App",
  components: { Stopwatch, UserVideo },
  data() {
    return {
      exitDialogVisible: false,
      questionNo: 1,
      start: true,
      recording: false,
      // finished: false,
      reportPk: this.$route.params.reportPk,
      newScore: 0,
      oldScore: 0,

      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      mySessionId:
        JSON.parse(sessionStorage.getItem("User")).id +
        "a" +
        new Date().getMinutes() +
        new Date().getSeconds() +
        new Date().getMilliseconds(),
      myUserName: "Participant" + Math.floor(Math.random() * 100),

      rtcPk: "",
    };
  },
  computed: {
    ...mapGetters(["isFinished", "question"]),
  },
  methods: {
    ...mapActions(["InterviewGeneral", "InterviewCs"]),

    startInterview() {
      //alert("Press StartInterviedw");
      let fetchInfo = {
        nextNo: this.questionNo,
        reportId: this.reportPk,
      };
      this.start = true;
      this.startRecording();
      if (this.$route.params.type == "general") {
        this.InterviewGeneral(fetchInfo);
      } else if (this.$route.params.type == "cs") {
        this.InterviewCs(fetchInfo);
      }

      // if (!this.isFinished) {
      // 	this.finished = true
      // }

      this.recording = !this.recording;
    },

    stopInterview() {
      //alert("Press StopInterview")
      this.stopRecording();
      this.leaveSession();
      this.mySessionId = this.mySessionId + this.questionNo;
      this.recording = !this.recording;

      let fetchInfo = {
        nextNo: this.questionNo,
        reportId: this.reportPk,
      };

      this.questionNo += 1;
      if (this.$route.params.type == "general") {
        this.InterviewGeneral(fetchInfo);
      } else if (this.$route.params.type == "cs") {
        this.InterviewCs(fetchInfo);
      }

      // console.log("rtcPk: ", this.rtcPk)
    },

    pause() {
      this.joinSession();
    },

    linkToEval() {
      if (this.$route.params.type == "general") {
        this.$router.push({
          name: "InterviewReviewGeneral",
          params: { reportPk: this.$route.params.reportPk },
        });
      } else if (this.$route.params.type == "cs") {
        this.$router.push({
          name: "InterviewReviewCs",
          params: { reportPk: this.$route.params.reportPk },
        });
      }
    },

    // --------------------------------------------------------------------------

    joinSession() {
      //alert("joinSession : " + this.mySessionId);
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((token) => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "800x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: true, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .then(() => {
            this.start = true;
            this.startRecording();

            let fetchInfo = {
              nextNo: this.questionNo,
              reportId: this.reportPk,
            };

            if (this.$route.params.type == "general") {
              this.InterviewGeneral(fetchInfo);
            } else if (this.$route.params.type == "cs") {
              this.InterviewCs(fetchInfo);
            }
            this.recording = !this.recording;
            this.questionNo += 1;
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      //alert("leaveSession : " + this.mySessionId);
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      //alert("getToken : " + mySessionId);
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      //alert("createSession: " + sessionId);
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    createToken(sessionId) {
      //alert("createToken : " + sessionId);
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },

    httpRequest(method, url, body, errorMsg, callback) {
      var http = new XMLHttpRequest();
      http.open(method, url, false);
      http.setRequestHeader("Content-type", "application/json");
      http.setRequestHeader(
        "Authorization",
        "Basic " + btoa("OPENVIDUAPP" + ":" + "MY_SECRET")
      );
      http.addEventListener("readystatechange", processRequest, false);
      http.send(JSON.stringify(body));

      function processRequest() {
        if (http.readyState == 4) {
          if (http.status == 200) {
            try {
              callback(JSON.parse(http.responseText));
            } catch (e) {
              console.log(e);
              callback(e);
            }
          } else {
            console.warn(errorMsg + " (" + http.status + ")");
            console.warn(http.responseText);
          }
        }
      }
    },

    startRecording() {
      var outputMode = "COMPOSED";
      var hasAudio = true;
      var hasVideo = true;

      //alert("startrecording(Req) : " + this.mySessionId);

      this.httpRequest(
        "POST",
        `${OPENVIDU_SERVER_URL}/openvidu/api/recordings/start`,
        {
          session: this.mySessionId,
          outputMode: "COMPOSED",
          hasAudio: true,
          hasVideo: true,
        },
        "Start recording WRONG",
        (res) => {
          //alert("startrecording(Res) : " + this.mySessionId);
          console.log(res);
        }
      );
    },

    stopRecording() {
      //alert("stop recording(Req) : " + this.mySessionId);
      this.httpRequest(
        "POST",
        `${OPENVIDU_SERVER_URL}/openvidu/api/recordings/stop/` +
          this.mySessionId,
        {},
        "Stop recording WRONG",
        (res) => {
          console.log(res);
          this.rtcPk = res.id;
          const req = {
            code: res.id,
            reportId: this.reportPk,
            userId: JSON.parse(sessionStorage.getItem("User")).id,
          };

          http.post("/webrtc", req).then((data) => {
            // console.log(data)
          });

          //alert("stop recording(Res) : " + this.mySessionId);
          // console.log("sessionid: ", this.sessizonId)
        }
      );
    },
  },
  created() {
    //alert("hello? >> created : " + this.mySessionId);
    this.joinSession();
  },
};
</script>

<style></style>
