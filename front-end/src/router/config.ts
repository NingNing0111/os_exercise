let routes = [
  {
    path: "/",
    name: "home",
    component: () => import("@/page/Home/Home.vue"),
  },
  {
    path: "/detail/:id",
    name: "detail",
    component: () => import("@/page/Detail/Detail.vue"),
  },
  {
    path: "/edit/:id",
    name: "edit",
    component: () => import("@/page/Edit/Edit.vue"),
  },
];

export default routes;
