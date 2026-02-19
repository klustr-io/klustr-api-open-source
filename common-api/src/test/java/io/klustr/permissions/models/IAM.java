package io.klustr.permissions.models;

import io.klustr.permissions.dsl.IAMChild;
import io.klustr.permissions.dsl.IAMPermission;
import io.klustr.permissions.dsl.IAMRelation;

public final class IAM {
    public static final class Orgs {
        public static final class Permissions {
            public static IAMPermission Create() { return () -> "orgs.create"; }
            public static IAMPermission Get() { return () -> "orgs.get"; }
            public static IAMPermission List() { return () -> "orgs.list"; }
            public static IAMPermission Update() { return () -> "orgs.update"; }
            public static IAMPermission Delete() { return () -> "orgs.delete"; }
            public static IAMPermission View() { return () -> "orgs.view"; }
        }

        public static final class Relations {
            public static IAMRelation Owners() { return () -> "orgs.owners"; }
            public static IAMRelation Admins() { return () -> "orgs.admins"; }
            public static IAMRelation Editors() { return () -> "orgs.editors"; }
            public static IAMRelation Viewers() { return () -> "orgs.viewers"; }
        }

        public static IdTarget Id(String id) { return new IdTarget("orgs", id); }
        public static IdTarget Any() { return new IdTarget("orgs", "*"); }

        public static final class Roles implements IAMChild {
            @Override public String namespace() { return "orgs.roles"; }

            public static final class Permissions {
                public static IAMPermission Create() { return () -> "orgs.roles.create"; }
                public static IAMPermission Get() { return () -> "orgs.roles.get"; }
                public static IAMPermission List() { return () -> "orgs.roles.list"; }
                public static IAMPermission Update() { return () -> "orgs.roles.update"; }
                public static IAMPermission Delete() { return () -> "orgs.roles.delete"; }
                public static IAMPermission View() { return () -> "orgs.roles.view"; }
            }

            public static final class Relations {
                public static IAMRelation Owners() { return () -> "orgs.roles.owners"; }
                public static IAMRelation Admins() { return () -> "orgs.roles.admins"; }
                public static IAMRelation Editors() { return () -> "orgs.roles.editors"; }
                public static IAMRelation Viewers() { return () -> "orgs.roles.viewers"; }
            }

            public static IdTarget Id(String id) { return new IdTarget("roles", id); }
            public static IdTarget In(String parentId) { return new IdTarget("roles", parentId); }

        }
        public static final class Groups implements IAMChild {
            @Override public String namespace() { return "orgs.groups"; }

            public static final class Permissions {
                public static IAMPermission Create() { return () -> "orgs.groups.create"; }
                public static IAMPermission Get() { return () -> "orgs.groups.get"; }
                public static IAMPermission List() { return () -> "orgs.groups.list"; }
                public static IAMPermission Update() { return () -> "orgs.groups.update"; }
                public static IAMPermission Delete() { return () -> "orgs.groups.delete"; }
                public static IAMPermission View() { return () -> "orgs.groups.view"; }
            }

            public static final class Relations {
                public static IAMRelation Owners() { return () -> "orgs.groups.owners"; }
                public static IAMRelation Admins() { return () -> "orgs.groups.admins"; }
                public static IAMRelation Editors() { return () -> "orgs.groups.editors"; }
                public static IAMRelation Viewers() { return () -> "orgs.groups.viewers"; }
            }

            public static IdTarget Id(String id) { return new IdTarget("groups", id); }
            public static IdTarget In(String parentId) { return new IdTarget("groups", parentId); }

        }
        public static final class Settings implements IAMChild {
            @Override public String namespace() { return "orgs.settings"; }

            public static final class Permissions {
                public static IAMPermission Create() { return () -> "orgs.settings.create"; }
                public static IAMPermission Get() { return () -> "orgs.settings.get"; }
                public static IAMPermission List() { return () -> "orgs.settings.list"; }
                public static IAMPermission Update() { return () -> "orgs.settings.update"; }
                public static IAMPermission Delete() { return () -> "orgs.settings.delete"; }
                public static IAMPermission View() { return () -> "orgs.settings.view"; }
            }

            public static final class Relations {
                public static IAMRelation Owners() { return () -> "orgs.settings.owners"; }
                public static IAMRelation Admins() { return () -> "orgs.settings.admins"; }
                public static IAMRelation Editors() { return () -> "orgs.settings.editors"; }
                public static IAMRelation Viewers() { return () -> "orgs.settings.viewers"; }
            }

            public static IdTarget Id(String id) { return new IdTarget("settings", id); }
            public static IdTarget In(String parentId) { return new IdTarget("settings", parentId); }

        }
        public static final class Consent implements IAMChild {
            @Override public String namespace() { return "orgs.consent"; }

            public static final class Permissions {
                public static IAMPermission Create() { return () -> "orgs.consent.create"; }
                public static IAMPermission Get() { return () -> "orgs.consent.get"; }
                public static IAMPermission List() { return () -> "orgs.consent.list"; }
                public static IAMPermission Update() { return () -> "orgs.consent.update"; }
                public static IAMPermission Delete() { return () -> "orgs.consent.delete"; }
                public static IAMPermission View() { return () -> "orgs.consent.view"; }
            }

            public static final class Relations {
                public static IAMRelation Owners() { return () -> "orgs.consent.owners"; }
                public static IAMRelation Admins() { return () -> "orgs.consent.admins"; }
                public static IAMRelation Editors() { return () -> "orgs.consent.editors"; }
                public static IAMRelation Viewers() { return () -> "orgs.consent.viewers"; }
            }

            public static IdTarget Id(String id) { return new IdTarget("consent", id); }
            public static IdTarget In(String parentId) { return new IdTarget("consent", parentId); }

            public static final class Scopes implements IAMChild {
                @Override public String namespace() { return "orgs.consent.scopes"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.consent.scopes.create"; }
                    public static IAMPermission Get() { return () -> "orgs.consent.scopes.get"; }
                    public static IAMPermission List() { return () -> "orgs.consent.scopes.list"; }
                    public static IAMPermission Update() { return () -> "orgs.consent.scopes.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.consent.scopes.delete"; }
                    public static IAMPermission View() { return () -> "orgs.consent.scopes.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.consent.scopes.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.consent.scopes.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.consent.scopes.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.consent.scopes.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("scopes", id); }
                public static IdTarget In(String parentId) { return new IdTarget("scopes", parentId); }

            }
        }
        public static final class Members implements IAMChild {
            @Override public String namespace() { return "orgs.members"; }

            public static final class Permissions {
                public static IAMPermission Create() { return () -> "orgs.members.create"; }
                public static IAMPermission Get() { return () -> "orgs.members.get"; }
                public static IAMPermission List() { return () -> "orgs.members.list"; }
                public static IAMPermission Update() { return () -> "orgs.members.update"; }
                public static IAMPermission Delete() { return () -> "orgs.members.delete"; }
                public static IAMPermission View() { return () -> "orgs.members.view"; }
            }

            public static final class Relations {
                public static IAMRelation Owners() { return () -> "orgs.members.owners"; }
                public static IAMRelation Admins() { return () -> "orgs.members.admins"; }
                public static IAMRelation Editors() { return () -> "orgs.members.editors"; }
                public static IAMRelation Viewers() { return () -> "orgs.members.viewers"; }
            }

            public static IdTarget Id(String id) { return new IdTarget("members", id); }
            public static IdTarget In(String parentId) { return new IdTarget("members", parentId); }

            public static final class Invitations implements IAMChild {
                @Override public String namespace() { return "orgs.members.invitations"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.members.invitations.create"; }
                    public static IAMPermission Get() { return () -> "orgs.members.invitations.get"; }
                    public static IAMPermission List() { return () -> "orgs.members.invitations.list"; }
                    public static IAMPermission Update() { return () -> "orgs.members.invitations.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.members.invitations.delete"; }
                    public static IAMPermission View() { return () -> "orgs.members.invitations.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.members.invitations.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.members.invitations.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.members.invitations.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.members.invitations.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("invitations", id); }
                public static IdTarget In(String parentId) { return new IdTarget("invitations", parentId); }

            }
        }
        public static final class Projects implements IAMChild {
            @Override public String namespace() { return "orgs.projects"; }

            public static final class Permissions {
                public static IAMPermission Create() { return () -> "orgs.projects.create"; }
                public static IAMPermission Get() { return () -> "orgs.projects.get"; }
                public static IAMPermission List() { return () -> "orgs.projects.list"; }
                public static IAMPermission Update() { return () -> "orgs.projects.update"; }
                public static IAMPermission Delete() { return () -> "orgs.projects.delete"; }
                public static IAMPermission View() { return () -> "orgs.projects.view"; }
            }

            public static final class Relations {
                public static IAMRelation Owners() { return () -> "orgs.projects.owners"; }
                public static IAMRelation Admins() { return () -> "orgs.projects.admins"; }
                public static IAMRelation Editors() { return () -> "orgs.projects.editors"; }
                public static IAMRelation Viewers() { return () -> "orgs.projects.viewers"; }
            }

            public static IdTarget Id(String id) { return new IdTarget("projects", id); }
            public static IdTarget In(String parentId) { return new IdTarget("projects", parentId); }

            public static final class Apps implements IAMChild {
                @Override public String namespace() { return "orgs.projects.apps"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.projects.apps.create"; }
                    public static IAMPermission Get() { return () -> "orgs.projects.apps.get"; }
                    public static IAMPermission List() { return () -> "orgs.projects.apps.list"; }
                    public static IAMPermission Update() { return () -> "orgs.projects.apps.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.projects.apps.delete"; }
                    public static IAMPermission View() { return () -> "orgs.projects.apps.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.projects.apps.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.projects.apps.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.projects.apps.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.projects.apps.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("apps", id); }
                public static IdTarget In(String parentId) { return new IdTarget("apps", parentId); }

            }
            public static final class Experiments implements IAMChild {
                @Override public String namespace() { return "orgs.projects.experiments"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.projects.experiments.create"; }
                    public static IAMPermission Get() { return () -> "orgs.projects.experiments.get"; }
                    public static IAMPermission List() { return () -> "orgs.projects.experiments.list"; }
                    public static IAMPermission Update() { return () -> "orgs.projects.experiments.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.projects.experiments.delete"; }
                    public static IAMPermission View() { return () -> "orgs.projects.experiments.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.projects.experiments.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.projects.experiments.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.projects.experiments.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.projects.experiments.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("experiments", id); }
                public static IdTarget In(String parentId) { return new IdTarget("experiments", parentId); }

            }
            public static final class Credentials implements IAMChild {
                @Override public String namespace() { return "orgs.projects.credentials"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.projects.credentials.create"; }
                    public static IAMPermission Get() { return () -> "orgs.projects.credentials.get"; }
                    public static IAMPermission List() { return () -> "orgs.projects.credentials.list"; }
                    public static IAMPermission Update() { return () -> "orgs.projects.credentials.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.projects.credentials.delete"; }
                    public static IAMPermission View() { return () -> "orgs.projects.credentials.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.projects.credentials.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.projects.credentials.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.projects.credentials.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.projects.credentials.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("credentials", id); }
                public static IdTarget In(String parentId) { return new IdTarget("credentials", parentId); }

                public static final class Clients implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.credentials.clients"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.credentials.clients.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.credentials.clients.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.credentials.clients.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.credentials.clients.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.credentials.clients.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.credentials.clients.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.credentials.clients.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.credentials.clients.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.credentials.clients.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.credentials.clients.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("clients", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("clients", parentId); }

                }
                public static final class Keys implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.credentials.keys"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.credentials.keys.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.credentials.keys.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.credentials.keys.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.credentials.keys.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.credentials.keys.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.credentials.keys.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.credentials.keys.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.credentials.keys.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.credentials.keys.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.credentials.keys.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("keys", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("keys", parentId); }

                }
                public static final class ServiceAccounts implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.credentials.service_accounts"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.credentials.service_accounts.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.credentials.service_accounts.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.credentials.service_accounts.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.credentials.service_accounts.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.credentials.service_accounts.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.credentials.service_accounts.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.credentials.service_accounts.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.credentials.service_accounts.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.credentials.service_accounts.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.credentials.service_accounts.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("service_accounts", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("service_accounts", parentId); }

                }
            }
            public static final class Storage implements IAMChild {
                @Override public String namespace() { return "orgs.projects.storage"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.projects.storage.create"; }
                    public static IAMPermission Get() { return () -> "orgs.projects.storage.get"; }
                    public static IAMPermission List() { return () -> "orgs.projects.storage.list"; }
                    public static IAMPermission Update() { return () -> "orgs.projects.storage.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.projects.storage.delete"; }
                    public static IAMPermission View() { return () -> "orgs.projects.storage.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.projects.storage.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.projects.storage.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.projects.storage.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.projects.storage.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("storage", id); }
                public static IdTarget In(String parentId) { return new IdTarget("storage", parentId); }

                public static final class Buckets implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.storage.buckets"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.storage.buckets.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.storage.buckets.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.storage.buckets.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.storage.buckets.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.storage.buckets.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.storage.buckets.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.storage.buckets.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.storage.buckets.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.storage.buckets.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.storage.buckets.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("buckets", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("buckets", parentId); }

                }
                public static final class Files implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.storage.files"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.storage.files.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.storage.files.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.storage.files.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.storage.files.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.storage.files.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.storage.files.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.storage.files.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.storage.files.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.storage.files.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.storage.files.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("files", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("files", parentId); }

                }
                public static final class Public implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.storage.public"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.storage.public.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.storage.public.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.storage.public.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.storage.public.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.storage.public.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.storage.public.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.storage.public.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.storage.public.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.storage.public.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.storage.public.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("public", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("public", parentId); }

                }
            }
            public static final class Kafka implements IAMChild {
                @Override public String namespace() { return "orgs.projects.kafka"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.projects.kafka.create"; }
                    public static IAMPermission Get() { return () -> "orgs.projects.kafka.get"; }
                    public static IAMPermission List() { return () -> "orgs.projects.kafka.list"; }
                    public static IAMPermission Update() { return () -> "orgs.projects.kafka.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.projects.kafka.delete"; }
                    public static IAMPermission View() { return () -> "orgs.projects.kafka.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.projects.kafka.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.projects.kafka.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.projects.kafka.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.projects.kafka.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("kafka", id); }
                public static IdTarget In(String parentId) { return new IdTarget("kafka", parentId); }

                public static final class Topics implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.kafka.topics"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.kafka.topics.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.kafka.topics.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.kafka.topics.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.kafka.topics.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.kafka.topics.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.kafka.topics.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.kafka.topics.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.kafka.topics.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.kafka.topics.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.kafka.topics.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("topics", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("topics", parentId); }

                }
                public static final class Messages implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.kafka.messages"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.kafka.messages.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.kafka.messages.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.kafka.messages.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.kafka.messages.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.kafka.messages.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.kafka.messages.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.kafka.messages.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.kafka.messages.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.kafka.messages.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.kafka.messages.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("messages", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("messages", parentId); }

                }
            }
            public static final class Collections implements IAMChild {
                @Override public String namespace() { return "orgs.projects.collections"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.projects.collections.create"; }
                    public static IAMPermission Get() { return () -> "orgs.projects.collections.get"; }
                    public static IAMPermission List() { return () -> "orgs.projects.collections.list"; }
                    public static IAMPermission Update() { return () -> "orgs.projects.collections.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.projects.collections.delete"; }
                    public static IAMPermission View() { return () -> "orgs.projects.collections.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.projects.collections.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.projects.collections.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.projects.collections.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.projects.collections.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("collections", id); }
                public static IdTarget In(String parentId) { return new IdTarget("collections", parentId); }

                public static final class Table implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.collections.table"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.collections.table.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.collections.table.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.collections.table.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.collections.table.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.collections.table.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.collections.table.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.collections.table.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.collections.table.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.collections.table.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.collections.table.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("table", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("table", parentId); }

                }
            }
            public static final class Members implements IAMChild {
                @Override public String namespace() { return "orgs.projects.members"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.projects.members.create"; }
                    public static IAMPermission Get() { return () -> "orgs.projects.members.get"; }
                    public static IAMPermission List() { return () -> "orgs.projects.members.list"; }
                    public static IAMPermission Update() { return () -> "orgs.projects.members.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.projects.members.delete"; }
                    public static IAMPermission View() { return () -> "orgs.projects.members.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.projects.members.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.projects.members.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.projects.members.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.projects.members.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("members", id); }
                public static IdTarget In(String parentId) { return new IdTarget("members", parentId); }

                public static final class Invitations implements IAMChild {
                    @Override public String namespace() { return "orgs.projects.members.invitations"; }

                    public static final class Permissions {
                        public static IAMPermission Create() { return () -> "orgs.projects.members.invitations.create"; }
                        public static IAMPermission Get() { return () -> "orgs.projects.members.invitations.get"; }
                        public static IAMPermission List() { return () -> "orgs.projects.members.invitations.list"; }
                        public static IAMPermission Update() { return () -> "orgs.projects.members.invitations.update"; }
                        public static IAMPermission Delete() { return () -> "orgs.projects.members.invitations.delete"; }
                        public static IAMPermission View() { return () -> "orgs.projects.members.invitations.view"; }
                    }

                    public static final class Relations {
                        public static IAMRelation Owners() { return () -> "orgs.projects.members.invitations.owners"; }
                        public static IAMRelation Admins() { return () -> "orgs.projects.members.invitations.admins"; }
                        public static IAMRelation Editors() { return () -> "orgs.projects.members.invitations.editors"; }
                        public static IAMRelation Viewers() { return () -> "orgs.projects.members.invitations.viewers"; }
                    }

                    public static IdTarget Id(String id) { return new IdTarget("invitations", id); }
                    public static IdTarget In(String parentId) { return new IdTarget("invitations", parentId); }

                }
            }
        }
        public static final class ApiCatalog implements IAMChild {
            @Override public String namespace() { return "orgs.api_catalog"; }

            public static final class Permissions {
                public static IAMPermission Create() { return () -> "orgs.api_catalog.create"; }
                public static IAMPermission Get() { return () -> "orgs.api_catalog.get"; }
                public static IAMPermission List() { return () -> "orgs.api_catalog.list"; }
                public static IAMPermission Update() { return () -> "orgs.api_catalog.update"; }
                public static IAMPermission Delete() { return () -> "orgs.api_catalog.delete"; }
                public static IAMPermission View() { return () -> "orgs.api_catalog.view"; }
            }

            public static final class Relations {
                public static IAMRelation Owners() { return () -> "orgs.api_catalog.owners"; }
                public static IAMRelation Admins() { return () -> "orgs.api_catalog.admins"; }
                public static IAMRelation Editors() { return () -> "orgs.api_catalog.editors"; }
                public static IAMRelation Viewers() { return () -> "orgs.api_catalog.viewers"; }
            }

            public static IdTarget Id(String id) { return new IdTarget("api_catalog", id); }
            public static IdTarget In(String parentId) { return new IdTarget("api_catalog", parentId); }

            public static final class Documentation implements IAMChild {
                @Override public String namespace() { return "orgs.api_catalog.documentation"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.api_catalog.documentation.create"; }
                    public static IAMPermission Get() { return () -> "orgs.api_catalog.documentation.get"; }
                    public static IAMPermission List() { return () -> "orgs.api_catalog.documentation.list"; }
                    public static IAMPermission Update() { return () -> "orgs.api_catalog.documentation.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.api_catalog.documentation.delete"; }
                    public static IAMPermission View() { return () -> "orgs.api_catalog.documentation.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.api_catalog.documentation.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.api_catalog.documentation.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.api_catalog.documentation.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.api_catalog.documentation.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("documentation", id); }
                public static IdTarget In(String parentId) { return new IdTarget("documentation", parentId); }

            }
            public static final class Endpoints implements IAMChild {
                @Override public String namespace() { return "orgs.api_catalog.endpoints"; }

                public static final class Permissions {
                    public static IAMPermission Create() { return () -> "orgs.api_catalog.endpoints.create"; }
                    public static IAMPermission Get() { return () -> "orgs.api_catalog.endpoints.get"; }
                    public static IAMPermission List() { return () -> "orgs.api_catalog.endpoints.list"; }
                    public static IAMPermission Update() { return () -> "orgs.api_catalog.endpoints.update"; }
                    public static IAMPermission Delete() { return () -> "orgs.api_catalog.endpoints.delete"; }
                    public static IAMPermission View() { return () -> "orgs.api_catalog.endpoints.view"; }
                }

                public static final class Relations {
                    public static IAMRelation Owners() { return () -> "orgs.api_catalog.endpoints.owners"; }
                    public static IAMRelation Admins() { return () -> "orgs.api_catalog.endpoints.admins"; }
                    public static IAMRelation Editors() { return () -> "orgs.api_catalog.endpoints.editors"; }
                    public static IAMRelation Viewers() { return () -> "orgs.api_catalog.endpoints.viewers"; }
                }

                public static IdTarget Id(String id) { return new IdTarget("endpoints", id); }
                public static IdTarget In(String parentId) { return new IdTarget("endpoints", parentId); }

            }
        }
    }
    private IAM() {}
}