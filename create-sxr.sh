git pull

sbt11 compile

rm -rf ../gh-pages/main/*
rm -rf ../gh-pages/test/*

cp target/scala-2.9.1/classes.sxr/* ../gh-pages/main/
cp target/scala-2.9.1/test-classes.sxr/* ../gh-pages/test/

cd ../gh-pages/

git pull

git add \*.html

git commit -a

