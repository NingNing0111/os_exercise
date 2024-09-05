import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
// 倒入router
import router from "./router";
// 导入Element Plus
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import "md-editor-v3/lib/style.css";

// 导入pinia
import { createPinia } from "pinia";
const pinia = createPinia();

const app = createApp(App);
// 使用router
app.use(router);
// 使用pinia
app.use(pinia);
// 使用Element Plus
app.use(ElementPlus);
app.mount("#app");
