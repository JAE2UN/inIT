<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img src="@/assets/images/inIT.png" alt="logo" class="header-logo">
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <!-- <el-aside width="220px">
          <side-bar/>
        </el-aside> -->
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box">
            <!-- interview view -->
            <div class="inner interview">
              <!-- v-if JoinSession -->
              <el-scrollbar class="inner-scroll">
                <el-row>
                  <div class="question-box" v-if="!recording && !start">
                    <div class="interview-question">
                      이 곳에 문제가 보여집니다.
                    </div>
                  </div>
                  <div class="question-box" v-if="recording && start">
                    <div class="interview-question">
                      {{ question.quest }}
                    </div>
                  </div>
                  <div class="question-box" v-if="!recording && start">
                    <div class="interview-question">
                      다음 문항으로 넘어가려면 버튼을 누르세요.
                    </div>
                  </div>
                </el-row>
                <el-row>
                  <div>
                    <user-video :stream-manager="mainStreamManager"/>
                    <stopwatch/>
                  </div>
                </el-row>
                <!-- v-if -->
                <el-row class="interview-check">
                  <el-col class="btn-box">
                  
                    <!-- <el-button
                      class="next-btn" 
                      @click="startInterview"
                      v-if="!recording && !start"  
                    >
                      연습 시작하기
                    </el-button>
                    <el-button
                      class="next-btn"
                      v-if="!recording && start"
                      @click="startInterview"
                      >
                        다음 문항
                      </el-button>
                    <el-button
                      class="next-btn"
                      v-if="recording && start"
                      @click="stopInterview"
                    >
                      답변 완료
                    </el-button> -->
                    <el-button
                      class="next-btn" 
                      @click="startInterview"
                    >
                      연습 시작하기
                    </el-button>
                    <el-button @click="hey">
                      중간
                    </el-button>
                    <el-button
                      class="next-btn"
                      @click="stopInterview"
                    >
                      답변 완료
                    </el-button>
                     
                    <el-button class="exit-btn" @click="exitDialogVisible = true">연습 종료</el-button>
                  </el-col>
                </el-row>
              </el-scrollbar>
              

              <!-- v-if startRecording -->
              <el-scrollbar class="inner-scroll" v-if="false">
                <el-row>
                  <div class="question-box">
                    <div class="interview-question">
                      v-if recording Q1. // v-if !recording 다음 문제를 시작하려면 스페이스바를 누르세요.
                    </div>
                  </div>
                </el-row>
                <el-row>
                  <div class="interviewing-video">
                    송출화면
                    <stopwatch/>
                  </div>
                </el-row>
                <!-- v-if -->
                <el-row class="interview-check">
                  <el-col class="btn-box">
                    <el-button class="next-btn">다음 문항으로</el-button>
                    <!-- <el-button class="exit-btn" @click="exitDialogVisible = true">연습 종료</el-button> -->
                  </el-col>
                </el-row>
              </el-scrollbar>
            </div>
          </div>
          <!-- exit dialog -->
          <el-dialog v-model="isVisible" custom-class="exit-dialog">
            <el-row class="dialog-row">
              <el-col class="dialog-box">
                <el-col class="thumbnail">
                  <div>
                    떰브네일
                  </div>
                </el-col>
                <el-col class="content">
                  <span class="first">연습이 종료되었어요!</span>
                </el-col>
                <el-col class="btn-box">
                  <el-button class="now-btn" @click="linkTo('InterviewReviewGeneral', 3)">리뷰하러 가기</el-button>
                </el-col>
                <el-col>
                  <template #footer>
                    <span class="dialog-footer">
                      <el-button @click="exitDialogVisible = false">Cancel</el-button>
                    </span>
                  </template>
                </el-col>
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
import axios from 'axios';
import { mapActions, mapGetters } from 'vuex'
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/views/interview/components/UserVideo.vue';
import Stopwatch from "./components/Stopwatch.vue"


axios.defaults.headers.post['Content-Type'] = 'application/json';

const OPENVIDU_SERVER_URL = "https://interview.ml:4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";


export default {
  name: 'App',
  components: { Stopwatch, UserVideo, },
  data() {
    return {
      exitDialogVisible: false,
      questionNo: 1,
      start: false,
      recording: false,
      imRated: false,
      isEditing: false,
      reportPk: this.$route.params.reportPk,
      newScore: 0,
      oldScore: 0,

      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],

      heyhey: "hey",
			mySessionId: sessionStorage.getItem("UserNick") + (new Date().getMinutes()) + (new Date().getSeconds()) + (new Date().getMilliseconds()),
			myUserName: 'Participant' + Math.floor(Math.random() * 100),

    }
  },
  computed: {
    ...mapGetters([ "isFinished", "question"]),
  },
  methods: {
    ...mapActions(['InterviewGeneral']),
    startInterview () {
      let fetchInfo = {
        nextNo: this.questionNo,
        reportId: this.reportPk,
      }
      this.start = true
      console.log("before start: ", this.mySessionId)
      this.startRecording()
      this.InterviewGeneral(fetchInfo)
      this.recording = !this.recording
      this.questionNo += 1
      console.log("recording started")
    },
    stopInterview () {
      this.stopRecording()
      this.leaveSession()
      this.mySessionId = this.mySessionId + this.questionNo
      console.log("sessionId changed: ", this.mySessionId)
      this.recording = !this.recording
      console.log("recording stopped")
    },
    hey () {
      this.joinSession()
      this.start = true
      this.startRecording()
      this.InterviewGeneral(fetchInfo)
      this.recording = !this.recording
      this.questionNo += 1
      console.log("recording started")
    },


// --------------------------------------------------------------------------


		joinSession () {
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '1280x720',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: true       	// Whether to mirror your local video or not
						});

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
		},

		leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
		},

		updateMainVideoStreamManager (stream) {
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

		getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
	
		httpRequest(method, url, body, errorMsg, callback) {
			var http = new XMLHttpRequest();
			http.open(method, url, true);
			http.setRequestHeader('Content-type', 'application/json');
			http.setRequestHeader('Authorization', "Basic " + btoa("OPENVIDUAPP" + ":" + "MY_SECRET"));
			http.addEventListener('readystatechange', processRequest, false);
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
						console.warn(errorMsg + ' (' + http.status + ')');
						console.warn(http.responseText);
					}
				}
			}
		},

		startRecording() {
			var outputMode = "COMPOSED"
			var hasAudio = true;
			var hasVideo = true;

			this.httpRequest(
				'POST',
				`${OPENVIDU_SERVER_URL}/openvidu/api/recordings/start`, {
					session: this.mySessionId,
					outputMode: "COMPOSED",
					hasAudio: true,
					hasVideo: true
				},
				'Start recording WRONG',
				res => {
					console.log(res);
				}
			);
		},

		stopRecording() {
			this.httpRequest(
				'POST',
				`${OPENVIDU_SERVER_URL}/openvidu/api/recordings/stop/` + this.mySessionId,
				{},
				'Stop recording WRONG',
				res => {
					console.log(res);
          console.log("sessionid: ", this.sessionId)
				}
			);
		},


  },
  created() {
    this.joinSession()
  },
}
</script>

<style>
</style>
