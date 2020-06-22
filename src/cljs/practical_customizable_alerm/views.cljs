(ns practical-customizable-alerm.views
  (:require
   [re-frame.core :as re-frame]
   [practical-customizable-alerm.subs :as subs]
   [practical-customizable-alerm.config :as conf]
   ))

(def home-svg
  [:svg {:width "54", :height "54", :viewBox "0 0 54 54", :fill "none", :xmlns "http://www.w3.org/2000/svg"}
   [:path {:d "M52.8802 25.8285L29.8651 2.1492L28.3224 0.561175C27.9709 0.201733 27.4956 0 27.0001 0C26.5047 0 26.0293 0.201733 25.6778 0.561175L1.12003 25.8285C0.759859 26.1979 0.47521 26.6377 0.282885 27.1221C0.0905606 27.6065 -0.00554309 28.1257 0.000246886 28.649C0.024072 30.8072 1.76926 32.5301 3.86588 32.5301H6.3973V52.5H47.6029V32.5301H50.1879C51.2065 32.5301 52.1654 32.1193 52.8861 31.3774C53.241 31.0133 53.5222 30.5803 53.7134 30.1036C53.9046 29.6268 54.002 29.1157 54 28.5999C54 27.5576 53.6009 26.5704 52.8802 25.8285V25.8285ZM30.3356 48.0854H23.6646V35.5774H30.3356V48.0854ZM43.3144 28.1155V48.0854H34.1477V34.1059C34.1477 32.7509 33.0815 31.6533 31.7651 31.6533H22.2351C20.9187 31.6533 19.8526 32.7509 19.8526 34.1059V48.0854H10.6858V28.1155H4.96779L27.0061 5.44788L28.382 6.86422L49.0384 28.1155H43.3144Z", :fill "black"}]])

(def help-svg
  [:svg {:width "54", :height "54", :viewBox "0 0 54 54", :fill "none", :xmlns "http://www.w3.org/2000/svg"}
   [:path
    {:fill "black"
     :d "M27 13.5C24.6533 13.503 22.4037 14.4365 20.7443 16.0958C19.085 17.7552 18.1515 20.0049 18.1485 22.3515H22.6485C22.6485 19.9507 24.6015 18 27 18C29.3985 18 31.3515 19.9507 31.3515 22.3515C31.3515 23.697 30.2692 24.6735 28.6155 26.01C28.0752 26.4332 27.5564 26.883 27.0607 27.3578C24.8152 29.601 24.75 31.9838 24.75 32.2493V33.75H29.25L29.2477 32.3257C29.25 32.2897 29.322 31.4573 30.24 30.5415C30.5775 30.204 31.0027 29.8665 31.4437 29.511C33.1965 28.0913 35.8492 25.947 35.8492 22.3515C35.8475 20.0049 34.9146 17.7548 33.2555 16.0953C31.5964 14.4358 29.3466 13.5024 27 13.5ZM24.75 36H29.25V40.5H24.75V36Z"}]
   [:path
    {:fill "black"
     :d "M27 4.5C14.5935 4.5 4.5 14.5935 4.5 27C4.5 39.4065 14.5935 49.5 27 49.5C39.4065 49.5 49.5 39.4065 49.5 27C49.5 14.5935 39.4065 4.5 27 4.5ZM27 45C17.0753 45 9 36.9248 9 27C9 17.0753 17.0753 9 27 9C36.9248 9 45 17.0753 45 27C45 36.9248 36.9248 45 27 45Z"}]])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        current-route @(re-frame/subscribe [:current-route])]
    [:div.container
     [:nav.navbar.bd-navbar.has-shadow.is-fixed-top
      {:role "navigation" :aria-label "main navigation" :style {:background-color "white"}}
      [:div.container>div.navbar-menu
       {:style {:align-items "center" :justify-content "space-around"}}
       [:div.navbar-item.start
        [:a {:href "#/" ;; (str conf/hashrouter-base "/#/")
             } home-svg]]
       [:div.navbar-item.end
        [:a {:href
             "#/about" ;; (str conf/hashrouter-base "/#/" "about")
             } help-svg]]]]
     [:div.container {:style {:margin-top "5.25rem"}}
      (when current-route
        [(-> current-route :data :view)])]]))

