import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./store"
import "./assets/tailwind.css"
import AOS from "aos"
import "aos/dist/aos.css"
import Loading from "@/components/Common/Loading.vue"
import ConfirmModal from "@/components/Common/ConfirmModal.vue"
import TextInput from "@/components/TextInput.vue"
import SelectInput from "@/components/SelectInput.vue"
import { setAxiosConfig } from "./lib/axios"

const app = createApp(App)

store
  .dispatch("auth/getMe", localStorage.getItem("accessToken"))
  .then(accessToken => {
    // axios 설정
    if (accessToken) {
      setAxiosConfig(accessToken)
    }
  })
  .finally(() => {
    // 컴포넌트 전역 등록
    app.component("ConfirmModal", ConfirmModal)
    app.component("TextInput", TextInput)
    app.component("SelectInput", SelectInput)
    app.component("Loading", Loading)

    app.use(store)
    app.use(router)
    app.use(AOS)
    app.mount("#app")
  })
