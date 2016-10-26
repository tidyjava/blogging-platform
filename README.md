# blogging-platform

Small blogging application, created for the purposes of [Tidy Java](http://tidyjava.com) blog. If you want to learn more
about how it's designed and built, there's no better place than the
[Blogging Platform series](http://tidyjava.com/tag/blogging-platform/). Project is Open Source
and "Unlicensed", so anybody can contribute or create her/his own project based on this one.

## Demo

#### Default configuration:
https://blogging-platform.herokuapp.com/
(can load a while, because it's running on free dyno)

#### Tidy Java configuration:
http://tidyjava.com/

## Getting Started
Here are a few simple steps to start using the platform:

1. Create a Git repository for your blog contents. (TIP: Every .md file is treated like a post so don't create a README.md!)
2. Fork this repository
3. Fill `application.yml` with your blog's name, url and content's repository url
4. Deploy the application (TIP: Heroku has great integration with GitHub. I already created a `Procfile` for you.)
5. Write your first blog post:
```
---
title: Hello World!
summary: My first post.
date: 1970-01-01
author: Awesome guy
tags:
    - whatever
---
Put your contents here using **markdown**:

# h1
## h2
### h3
...

*italic*
**bold**
==highlighted==

and more..
```

If you find this description not thorough enough, please let me know in the issues tab!!

### Enabling Disqus & Analytics
Disqus and Google Analytics are disabled by default. To enable them you have to modify `application.yml` and provide your Disqus name and/or Google Analytics tracking code.

```
analytics:
  enabled: true
  trackingCode: PUT_YOUR_TRACKING_CODE_HERE

disqus:
  enabled: true
  name: PUT_YOUR_DISQUS_NAME_HERE
```

### Social Links
Social links are external URLs displayed in the top right corner of the page. By default every blog contains an RSS link. You can configure extra links in `application.yml` e.g.

```
social:
  links:
    - name: Twitter
      url: http://twitter.com/grzegorztj
    - name: GitHub
      url: http://github.com/tidyjava
    - name: RSS
      url: http://tidyjava.com/rss
```

## Build & Run
Blogging Platform is a typical Spring Boot application with Gradle configuration. You can run it in multiple ways.

### Using IDE
To run the project from your IDE, just run `main` function in `BloggingPlatform` class.

### Using Gradle
To build the project, you can use the following command:
```
gradle assemble
```

To run the project use:
```
gradle bootRun
```

## Contribution guidelines
Coding style:
* Use Intellij's default formatting settings. If you're using a different IDE, make sure to configure it accordingly.

How to contribute?
* Get the project.
* Write some good code.
* Issue a pull request.

If you don't know where to start, check out issues tab - there's a lot of interesting stuff there!
