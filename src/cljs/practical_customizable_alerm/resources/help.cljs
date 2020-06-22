(ns practical-customizable-alerm.resources.help)

(def help
  {:title [:h1.title "よくある質問"]
   :contents
   [:div.container.px-5.py-5
    [:p "以下から見つからない場合は、"  [:a {:href "mailto:meguru.mokke@gmail.com"} "meguru.mokke@gmail.com"] "へお問い合わせ頂くか、以下のお問い合わせフォームをご利用下さい"]]})

(def help-list
  [{:id "sound-source-access"
    :title
    [:h2.sub-title.py-3 "Q. 音声のリンクはどうやって入手すればいいですか？"]
    :body
    [:div
     [:h2.sub-title.py-3 "ご自身の音源を利用する場合"]
     [:p "Google Drive の共有機能などをご活用下さい"]
     [:h2.sub-title.py-3 "公式素材を利用する場合"]
     [:p "こちらの" "公式素材集へ" "アクセスして下さい"]]}
   {:id "get-app"
    :title
    [:h2.sub-title.py-3 "Q. どうやってアプリを入手すればいいですか？"]
    :body
    [:div
     [:p "現在 Google Play Store での配信を行っていません"]
     [:p "もうしばらくお待ち下さい"]]}])
