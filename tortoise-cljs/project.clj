(defproject tortoise-cljs "0.0.1-SNAPSHOT"
  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-ring "0.8.11"]]
  :description "tortoise in clojurescript"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]]
  :cljsbuild {
    :repl-listen-port 9000
    :repl-launch-commands
      {"chromium" ["chromium-browser" "http://localhost:9000"]}
    :builds {
      :default {
        :source-paths ["src/tortoise_cljs/"]
        :compiler {
          :output-to "dist/bundle.js"
          :optimizations :whitespace
          :pretty-print true }}}})
