// Compiled by ClojureScript 0.0-2268
goog.provide('world');
goog.require('cljs.core');
goog.require('topology.vars');
goog.require('topology.vars');
world._id = cljs.core.gensym.call(null,"world_");
world.inited_QMARK_ = false;
world._topology_generator = new cljs.core.Keyword(null,"NONE","NONE",555544038).cljs$core$IFn$_invoke$arity$1(topology.vars.terraformer);
world._topology_type = new cljs.core.Keyword(null,"NONE","NONE",555544038);
world._size = cljs.core.atom.call(null,(0));
world.patchset = cljs.core.atom.call(null,null);
world.size = (function size(){return cljs.core.deref.call(null,world._size);
});
world._set_bounds = (function _set_bounds(x1,x2,y1,y2){topology.vars.min_pxcor = x1;
topology.vars.max_pxcor = x2;
topology.vars.min_pycor = y1;
return topology.vars.max_pycor = y2;
});
world._gen_patchset = (function _gen_patchset(mnx,mxx,mny,mxy){return cljs.core.with_meta.call(null,cljs.core.vec.call(null,(function (){var iter__4334__auto__ = (function iter__20437(s__20438){return (new cljs.core.LazySeq(null,(function (){var s__20438__$1 = s__20438;while(true){
var temp__4126__auto__ = cljs.core.seq.call(null,s__20438__$1);if(temp__4126__auto__)
{var xs__4624__auto__ = temp__4126__auto__;var w = cljs.core.first.call(null,xs__4624__auto__);var iterys__4330__auto__ = ((function (s__20438__$1,w,xs__4624__auto__,temp__4126__auto__){
return (function iter__20439(s__20440){return (new cljs.core.LazySeq(null,((function (s__20438__$1,w,xs__4624__auto__,temp__4126__auto__){
return (function (){var s__20440__$1 = s__20440;while(true){
var temp__4126__auto____$1 = cljs.core.seq.call(null,s__20440__$1);if(temp__4126__auto____$1)
{var s__20440__$2 = temp__4126__auto____$1;if(cljs.core.chunked_seq_QMARK_.call(null,s__20440__$2))
{var c__4332__auto__ = cljs.core.chunk_first.call(null,s__20440__$2);var size__4333__auto__ = cljs.core.count.call(null,c__4332__auto__);var b__20442 = cljs.core.chunk_buffer.call(null,size__4333__auto__);if((function (){var i__20441 = (0);while(true){
if((i__20441 < size__4333__auto__))
{var h = cljs.core._nth.call(null,c__4332__auto__,i__20441);cljs.core.chunk_append.call(null,b__20442,new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"idx","idx",1053688473),cljs.core.swap_BANG_.call(null,world._size,cljs.core.inc),new cljs.core.Keyword(null,"x","x",2099068185),w,new cljs.core.Keyword(null,"y","y",-1757859776),h,new cljs.core.Keyword(null,"inhabitants","inhabitants",-955228666),cljs.core.atom.call(null,null)], null));
{
var G__20443 = (i__20441 + (1));
i__20441 = G__20443;
continue;
}
} else
{return true;
}
break;
}
})())
{return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__20442),iter__20439.call(null,cljs.core.chunk_rest.call(null,s__20440__$2)));
} else
{return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__20442),null);
}
} else
{var h = cljs.core.first.call(null,s__20440__$2);return cljs.core.cons.call(null,new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"idx","idx",1053688473),cljs.core.swap_BANG_.call(null,world._size,cljs.core.inc),new cljs.core.Keyword(null,"x","x",2099068185),w,new cljs.core.Keyword(null,"y","y",-1757859776),h,new cljs.core.Keyword(null,"inhabitants","inhabitants",-955228666),cljs.core.atom.call(null,null)], null),iter__20439.call(null,cljs.core.rest.call(null,s__20440__$2)));
}
} else
{return null;
}
break;
}
});})(s__20438__$1,w,xs__4624__auto__,temp__4126__auto__))
,null,null));
});})(s__20438__$1,w,xs__4624__auto__,temp__4126__auto__))
;var fs__4331__auto__ = cljs.core.seq.call(null,iterys__4330__auto__.call(null,cljs.core.range.call(null,mny,(mxy + (1)))));if(fs__4331__auto__)
{return cljs.core.concat.call(null,fs__4331__auto__,iter__20437.call(null,cljs.core.rest.call(null,s__20438__$1)));
} else
{{
var G__20444 = cljs.core.rest.call(null,s__20438__$1);
s__20438__$1 = G__20444;
continue;
}
}
} else
{return null;
}
break;
}
}),null,null));
});return iter__4334__auto__.call(null,cljs.core.range.call(null,mnx,(mxx + (1))));
})()),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"SHAPE","SHAPE",996071722),world._topology_type], null));
});
world.init = (function init(tt,mnx,mxx,mny,mxy){if(cljs.core.not.call(null,world.inited_QMARK_))
{cljs.core.reset_BANG_.call(null,world._size,(0));
world._set_bounds.call(null,mnx,mxx,mny,mxy);
world._topology_type = tt;
cljs.core.reset_BANG_.call(null,world.patchset,world._gen_patchset.call(null,topology.vars.min_pxcor,topology.vars.max_pxcor,topology.vars.min_pycor,topology.vars.max_pycor));
return world.inited_QMARK_ = true;
} else
{return null;
}
});
world.get_patch_at = (function get_patch_at(x,y){return cljs.core.nth.call(null,cljs.core.drop.call(null,(((topology.vars.max_pycor - topology.vars.min_pycor) + (1)) * (x - topology.vars.min_pxcor)),cljs.core.deref.call(null,world.patchset)),(y - topology.vars.min_pycor));
});
