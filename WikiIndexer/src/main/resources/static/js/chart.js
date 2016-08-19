var articlesList = null;
var currentArticle = null;
var mainArticle = null;
var wordsChart = null;
var articlesChart = null;

function onArticleMouseover(e) {
    currentArticle = articlesList[e.dataPointIndex];
    wordsChart.options.data[0].dataPoints = getWords(currentArticle);
    wordsChart.options.title.text = 'Top words in "' + currentArticle.title + '"';
    wordsChart.render();
}

function onArticleMouseout(e) {
    currentArticle = mainArticle;
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

function makeChart(articleList) {

    var wordsTitle = null;
    articlesList = articleList;
    mainArticle = articleList[articleList.length - 1];
    currentArticle = mainArticle;
    if (articleList.length == 1) {
        wordsTitle = 'Top words in "' + articleList[0].title + '"';
    }
    /* More than one article */
    else {
        wordsTitle = "Top words in all articles";

        /* Make div for articles */
        var articlesDiv = document.createElement('div');
        articlesDiv.id = 'articlesChart';
        articlesDiv.style.height = "300px";
        articlesDiv.style.width  = "100%";
        document.getElementsByTagName('body')[0].appendChild(articlesDiv);

        /* Make articles pie chart */
        articlesChart = new CanvasJS.Chart("articlesChart", {
            theme: "theme2",
            title:{
                text: "Articles"
            },
            animationEnabled: false,
            toolTip:{
                enabled: true,
                animationEnabled: false
            },
            data: [
                {
                    type: "pie",
                    explodeOnClick: false,
                    dataPoints: getArticles(articleList)
                }
            ]
        });
        articlesChart.render();
    }

    /* Make words div */
    var wordsDiv = document.createElement('div');
    wordsDiv.id = 'wordsChart';
    wordsDiv.style.height = "300px";
    wordsDiv.style.width  = "100%";
    document.getElementsByTagName('body')[0].appendChild(wordsDiv);

    /* Make words chart */
    wordsChart = new CanvasJS.Chart("wordsChart", {
        theme: "theme2",
        title:{
            text: wordsTitle
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