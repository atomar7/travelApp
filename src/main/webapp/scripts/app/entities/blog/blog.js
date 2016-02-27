'use strict';

angular.module('travelappApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('blog', {
                parent: 'entity',
                url: '/blogs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Blogs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/blog/blogs.html',
                        controller: 'BlogController'
                    }
                },
                resolve: {
                }
            })
            .state('blog.detail', {
                parent: 'entity',
                url: '/blog/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Blog'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/blog/blog-detail.html',
                        controller: 'BlogDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Blog', function($stateParams, Blog) {
                        return Blog.get({id : $stateParams.id});
                    }]
                }
            })
            .state('blog.new', {
                parent: 'blog',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/blog/blog-dialog.html',
                        controller: 'BlogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    blog: null,
                                    imageUrls: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('blog', null, { reload: true });
                    }, function() {
                        $state.go('blog');
                    })
                }]
            })
            .state('blog.edit', {
                parent: 'blog',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/blog/blog-dialog.html',
                        controller: 'BlogDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Blog', function(Blog) {
                                return Blog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('blog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('blog.delete', {
                parent: 'blog',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/blog/blog-delete-dialog.html',
                        controller: 'BlogDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Blog', function(Blog) {
                                return Blog.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('blog', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
