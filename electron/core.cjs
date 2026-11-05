const path=require('path');
exports.safeName=n=>path.basename(String(n)).replace(/[<>:"/\\|?*\x00-\x1f]/g,'_').slice(0,180)||'book';
exports.supported=n=>/\.(epub|pdf|cbz|cbr)$/i.test(n);
exports.natural=(a,b)=>a.localeCompare(b,undefined,{numeric:true,sensitivity:'base'});
exports.safeEntry=n=>!n.split(/[\\/]/).includes('..')&&!path.isAbsolute(n)&&! /^[A-Za-z]:/.test(n);
