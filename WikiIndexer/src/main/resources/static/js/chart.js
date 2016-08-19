var articlesList = null;
var currentArticle = null;
var mainArticle = null;
var wordsChart = null;
var articlesChart = null;

function onArticleMouseover(e) {
    currentArticle = articlesList[e.dataPointIndex];
    switchSourceTime();
    wordsChart.options.data[0].dataPoints = getWords(currentArticle);
    wordsChart.options.title.text = 'Top words in "' + currentArticle.title + '"';
    wordsChart.render();
}

function onArticleMouseout(e) {
    currentArticle = mainArticle;
    switchSourceTime();
    wordsChart.options.data[0].dataPoints = getWords(currentArticle);
    wordsChart.options.title.text = 'Top words in all articles';
    wordsChart.render();
}

function onWordMouseover(e) {
    if (articlesChart) {
        var word = e.dataPoint.label;
        articlesChart.options.data[0].dataPoints = getArticlesByWord(word);
        articlesChart.options.title.text = 'Articles containing "' + word + '"';
        articlesChart.render();
    }
}

function onWordMouseout(e) {
    if (articlesChart) {
        var word = e.dataPoint.label;
        articlesChart.options.data[0].dataPoints = getArticles(articlesList);
        articlesChart.options.title.text = 'Articles';
        articlesChart.render();
    }
}

function getWords(article) {
    var words = [];
    for (var i in article.wordList) {
        words.push({label: article.wordList[i].word, y: article.wordList[i].nrAppar, mouseover: onWordMouseover, mouseout: onWordMouseout});
    }
    return words;
}

function getArticles() {
    var articles = [];
    for (var i = 0; i < articlesList.length - 1; i++) {
        var sum = getWords(articlesList[i]).reduce(function (a, b) {
            return a + b.y;
        }, 0);
        articles.push({label: articlesList[i].title, y: sum, mouseover: onArticleMouseover, mouseout: onArticleMouseout});
    }
    return articles;
}

function getArticlesByWord(word) {
    var articles = [];
    for (var i = 0; i < articlesList.length - 1; i++) {
        for (var j in articlesList[i].wordList) {
            if (articlesList[i].wordList[j].word == word) {
                articles.push({label: articlesList[i].title, y: articlesList[i].wordList[j].nrAppar});
                break;
            }
        }
    }
    return articles;
}

function switchSourceTime() {
    /* Save source */
    if (currentArticle.fromDatabase) {
        document.getElementById('source').innerHTML = 'Source: ' + 'From database';
    }
    else {
        document.getElementById('source').innerHTML = 'Sournce: ' + 'Fresh from Wikipedia';
    }
    /* Save seconds */
    var seconds = currentArticle.time / Math.pow(10, 9);
    document.getElementById('time').innerHTML = ' Time: ' + seconds + ' s';
}

function makeChart(articleList) {

    var wordsTitle = null;
    articlesList = articleList;
    mainArticle = articleList[articleList.length - 1];
    currentArticle = mainArticle;

    switchSourceTime();

    if (articleList.length == 1) {
        wordsTitle = 'Top words in "' + articleList[0].title + '"';
    }
    /* More than one article */
    else {
        wordsTitle = "Top words in all articles";

        /* Make articles pie chart */
        articlesChart = new CanvasJS.Chart("articlesChart", {
            theme: "theme2",
            backgroundColor: "",
            title:{
                text: "Articles",
                fontColor: "white",
                fontSize: 30
            },
            animationEnabled: false,
            toolTip:{
                enabled: true,
                animationEnabled: false
            },
            data: [
                {
                    indexLabelFontColor: "white",
                    type: "pie",
                    explodeOnClick: false,
                    dataPoints: getArticles(articleList)
                }
            ]
        });
        articlesChart.render();
    }

    /* Make words chart */
    CanvasJS.addColorSet("greenShades",
        [//colorSet Array

            "#5A6351",
            "#2F4F4F",
            "#008080",
            "#2E8B57",
            "#3CB371",
            "#90EE90",
            "#37BC61",
            "#00FF7F",
            "#00CD00",
            "#76EE00"

        ]);

    wordsChart = new CanvasJS.Chart("wordsChart", {
        theme: "theme2",
        backgroundColor:"",
        colorSet:"greenShades",
        axisX:{
            title: "Words",
            titleFontColor: "white",
            labelFontColor: "white",
            titleFontWeight: "bold",
            titleFontFamily: "arial"

        },
        axisY:{
            title: "Apparition number",
            titleFontColor: "white",
            labelFontColor: "white",
            titleFontWeight: "bold",
            titleFontFamily: "arial"


        },
        title:{
            text: wordsTitle,
            fontColor: "white",
            fontSize: 30,
        },
        animationEnabled: false,
        toolTip:{
            enabled: true,
            animationEnabled: false
        },
        data: [
            {
                type: "column",
                dataPoints: getWords(currentArticle)
            }
        ]
    });
    wordsChart.render();
}