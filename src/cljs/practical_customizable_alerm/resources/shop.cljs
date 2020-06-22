(ns practical-customizable-alerm.resources.shop
  (:require [practical-customizable-alerm.config :as conf]))

(def shop
  {:title [:h1.title>span.sec-title "公式素材"]
   :contents
   [:div.container.px-5.py-5
    [:p "自作と自由を売りにしたPCAですが"]
    [:p "サンプルとして公式素材を提供しています"]
    [:br]
    [:p "提供物としては"]
    [:p "アラームの 3D モデルデータ"]
    [:p "目覚まし音声・楽曲になります"]
    [:br]
    [:p "また、目覚まし音声として"]
    [:p "近年注目を浴びている"]
    [:p "AIきりたんを利用した楽曲とその作成方法を紹介しています"]]
   :subillust
   [:div.container
    {:style {:margin "3rem 3rem 3rem 0rem"}}
    [:svg {:viewBox "0 0 467 467" :fill "none" :xmlns "http://www.w3.org/2000/svg"}
     [:path {:fill "#4B4B4B"
             :d "M281.917 200.75C275.191 200.092 268.915 200.045 263.041 200.487C260.688 
190.836 258.219 180.977 255.681 170.831C252.988 160.097 250.635 150.097 
248.585 140.733C278.876 110.372 298.479 83.4249 302.488 59.8742C307.379 31.2085 296.188 -1.20335 264.977 0.034382C233.765 1.28124 205.091 32.4462 215.028 96.0312C217.18 109.791 219.935 123.211 223.077 136.553C215.802 142.126 207.8 147.76 199.04 153.378C105.635 213.217 124.403 305.469 149.346 336.634C169.042 361.252 221.53 382.736 268.831 367.358C266.021 400.652 255.875 425.982 241.357 436.368C219.796 451.809 207.552 440.106 207.956 430.13C208.35 420.155 207.793 402.703 195.48 398.966C183.152 395.228 169.547 392.736 157.79 406.448C146.019 420.155 151.352 442.599 162.218 451.321C186.65 470.932 229.696 477.525 268.693 438.86C286.408 421.292 292.661 393.818 291.198 356.863C295.857 353.915 300.408 350.51 304.788 346.601C357.783 299.238 345.254 206.979 281.917 200.75ZM259.999 31.2075C291.311 32.7016 293.208 72.1476 244.102 118.683C232.408 55.4231 238.925 30.2015 259.999 31.2075ZM176.469 312.944C147.084 268.065 163.6 217.002 208.007 178.306C215.304 171.952 222.27 165.714 228.879 159.616C232.842 174.46 237.138 189.404 241.502 204.797C207.689 216.56 193.99 248.399 196.808 271.81C200.546 302.976 222.796 307.96 235.263 307.96C247.732 307.96 253.249 294.245 237.252 289.262C223.299 284.913 220.219 245.573 248.714 230.568C253.854 249.429 258.9 269.21 263.38 290.5C266.476 305.228 268.341 319.522 269.138 333.074C236.998 347.987 198.418 346.494 176.469 312.944ZM298.64 312.944C295.32 316.141 291.737 319.136 287.991 321.899C284.206 293.728 277.512 261.432 268.961 225.144C309.398 223.503 330.912 281.91 298.64 312.944Z"
             }]
     ]]
   :link {:text "公式素材集 →"
          :url (str "#/" "shops/model")}
   })


(def shop-items
  [{:id "guitar-1"
    :title "ギター 1"
    :img (str conf/hashrouter-base "img/acoustic-guitar.png")
    :detail-title "ベーシック・ギター"
    :detail-description [:p "ベーシックなギターのサンプル"]
    :link [:a {:href "https://ja.wikipedia.org/wiki/%E3%82%AE%E3%82%BF%E3%83%BC"} "モデルリンク"]}
   {:id "guitar-2"
    :title "ギター 2"
    :img (str conf/hashrouter-base "img/acoustic-guitar.png")
    :detail-title "ベーシック・ギター"
    :detail-description [:p "ベーシックなギターのサンプル"]
    :link [:a {:href "https://ja.wikipedia.org/wiki/%E3%82%AE%E3%82%BF%E3%83%BC"} "モデルリンク"]}
   {:id "guitar-3"
    :title "ギター 3"
    :img (str conf/hashrouter-base "img/acoustic-guitar.png")
    :detail-title "ベーシック・ギター"
    :detail-description [:p "ベーシックなギターのサンプル"]
    :link [:a {:href "https://ja.wikipedia.org/wiki/%E3%82%AE%E3%82%BF%E3%83%BC"} "モデルリンク"]}])

(def character-list
  [{:id "hatsune-miku"
    :title
    [:h2.sub-title.py-3 "初音ミク (外部素材)"]
    :body
    [:div {:style {:text-align "center"}}
     [:p "初音ミクの 3D モデル"]
     [:p "本モデルは公式のものではありません。"]
     [:p [:a {:href "https://piapro.jp/t/0Hwp"} "リンク元"]]
     [:figure
      [:img {:src "//cdn.piapro.jp/thumb_m/rf/rfd90tbmj7h7fzzd_20200608220237_0740_0500.png"}]]]
    }
   {:id "akari"
    :title
    [:h2.sub-title.py-3 "つみ式紲星あかり (外部素材)"]
    :body
    [:div {:style {:text-align "center"}}
     [:p "紲星あかりの 3D モデル"]
     [:p "本モデルは公式のものではありません。"]
     [:p [:a {:href "https://3d.nicovideo.jp/works/td39238"} "リンク元"]]
     [:figure
      [:img {:src (str conf/hashrouter-base "img/akari.png")}]]]}])

(def song-list
  [{:id "basic-code"
    :title
    [:h2.sub-title.py-3 "ベーシックなCコード進行"]
    :body
    [:div {:style {:text-align "center"}}
     [:p "ベーシックなCコード進行です。詞とかないです"]
     [:a.sub-title {:href "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fbasic-code.wav?alt=media&token=eabf88cf-3428-4642-90f9-293763071bcd"}
      "リンク"]]}
   {:id "pretender"
    :title
    [:h2.sub-title.py-3 "Pretender Mix with NEUTORINO"]
    :body
    [:div {:style {:text-align "center"}}
     [:p "Pretender を NEUTORINO のきりたんに歌わせてみたものです"]
     [:p "NEUTORINO へ入力した musicxml は"
      [:a {:href "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fpretender-main.musicxml?alt=media&token=ec89ece7-0171-4e85-a2f0-16cff2115ca1"}
       "こちら"]
      "になります"]
     [:a.sub-title {:href "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fpretender-main_syn.wav?alt=media&token=9069b4e6-6a3e-4ca6-ba6d-8a66af861abf"}
      "リンク"]]}
   {:id "pretender-with-piano"
    :title
    [:h2.sub-title.py-3 "Pretender Mix with NEUTORINO x Ardour"]
    :body
    [:div {:style {:text-align "center"}}
     [:p "Pretender を NEUTORINO のきりたんに歌わせてみたものにピアノなどの伴奏を追加したものです"]
     [:a.sub-title {:href "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fpretender-with-piano.ogg?alt=media&token=d2fe752f-063f-4125-ba4e-43c4500f50d4"}
      "リンク"]]}])

(def voice-list
  [{:id "basic-code"
    :title
    [:h2.sub-title.py-3 "ベーシックなCコード進行"]
    :body
    [:div {:style {:text-align "center"}}
     [:p "ベーシックなCコード進行です。詞とかないです"]
     [:a.sub-title {:href "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fbasic-code.wav?alt=media&token=eabf88cf-3428-4642-90f9-293763071bcd"}
      "リンク"]]}
   {:id "pretender"
    :title
    [:h2.sub-title.py-3 "Pretender Mix with NEUTORINO"]
    :body
    [:div {:style {:text-align "center"}}
     [:p "Pretender を NEUTORINO のきりたんに歌わせてみたものです"]
     [:p "NEUTORINO へ入力した musicxml は"
      [:a {:href "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fpretender-main.musicxml?alt=media&token=ec89ece7-0171-4e85-a2f0-16cff2115ca1"}
       "こちら"]
      "になります"]
     [:a.sub-title {:href "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fpretender-main_syn.wav?alt=media&token=9069b4e6-6a3e-4ca6-ba6d-8a66af861abf"}
      "リンク"]]}
   {:id "pretender-with-piano"
    :title
    [:h2.sub-title.py-3 "Pretender Mix with NEUTORINO x Ardour"]
    :body
    [:div {:style {:text-align "center"}}
     [:p "Pretender を NEUTORINO のきりたんに歌わせてみたものにピアノなどの伴奏を追加したものです"]
     [:a.sub-title {:href "https://firebasestorage.googleapis.com/v0/b/personalcustomizablealerm.appspot.com/o/official_sample%2Fpretender-with-piano.ogg?alt=media&token=d2fe752f-063f-4125-ba4e-43c4500f50d4"}
      "リンク"]]}])
