// This file is generated by omniidl (C++ backend)- omniORB_4_3. Do not edit.
#ifndef __bootstrap_hh__
#define __bootstrap_hh__

#ifndef __CORBA_H_EXTERNAL_GUARD__
#include <omniORB4/CORBA.h>
#endif

#ifndef  USE_stub_in_nt_dll
# define USE_stub_in_nt_dll_NOT_DEFINED_bootstrap
#endif
#ifndef  USE_core_stub_in_nt_dll
# define USE_core_stub_in_nt_dll_NOT_DEFINED_bootstrap
#endif
#ifndef  USE_dyn_stub_in_nt_dll
# define USE_dyn_stub_in_nt_dll_NOT_DEFINED_bootstrap
#endif






#ifdef USE_stub_in_nt_dll
# ifndef USE_core_stub_in_nt_dll
#  define USE_core_stub_in_nt_dll
# endif
# ifndef USE_dyn_stub_in_nt_dll
#  define USE_dyn_stub_in_nt_dll
# endif
#endif

#ifdef _core_attr
# error "A local CPP macro _core_attr has already been defined."
#else
# ifdef  USE_core_stub_in_nt_dll
#  define _core_attr _OMNIORB_NTDLL_IMPORT
# else
#  define _core_attr
# endif
#endif

#ifdef _dyn_attr
# error "A local CPP macro _dyn_attr has already been defined."
#else
# ifdef  USE_dyn_stub_in_nt_dll
#  define _dyn_attr _OMNIORB_NTDLL_IMPORT
# else
#  define _dyn_attr
# endif
#endif



#ifndef __CORBA__InitialReferences__
#define __CORBA__InitialReferences__
class CORBA_InitialReferences;
class _objref_CORBA_InitialReferences;
class _impl_CORBA_InitialReferences;

typedef _objref_CORBA_InitialReferences* CORBA_InitialReferences_ptr;
typedef CORBA_InitialReferences_ptr CORBA_InitialReferencesRef;

class CORBA_InitialReferences_Helper {
public:
  typedef CORBA_InitialReferences_ptr _ptr_type;

  static _ptr_type _nil();
  static _CORBA_Boolean is_nil(_ptr_type);
  static void release(_ptr_type);
  static void duplicate(_ptr_type);
  static void marshalObjRef(_ptr_type, cdrStream&);
  static _ptr_type unmarshalObjRef(cdrStream&);
};

typedef _CORBA_ObjRef_Var<_objref_CORBA_InitialReferences, CORBA_InitialReferences_Helper> CORBA_InitialReferences_var;
typedef _CORBA_ObjRef_OUT_arg<_objref_CORBA_InitialReferences,CORBA_InitialReferences_Helper > CORBA_InitialReferences_out;

#endif

// interface CORBA_InitialReferences
class CORBA_InitialReferences {
public:
  // Declarations for this interface type.
  typedef CORBA_InitialReferences_ptr _ptr_type;
  typedef CORBA_InitialReferences_var _var_type;

  static _ptr_type _duplicate(_ptr_type);
  static _ptr_type _narrow(::CORBA::Object_ptr);
  static _ptr_type _unchecked_narrow(::CORBA::Object_ptr);
  
  static _ptr_type _nil();

  static inline void _marshalObjRef(_ptr_type, cdrStream&);

  static inline _ptr_type _unmarshalObjRef(cdrStream& s) {
    omniObjRef* o = omniObjRef::_unMarshal(_PD_repoId,s);
    if (o)
      return (_ptr_type) o->_ptrToObjRef(_PD_repoId);
    else
      return _nil();
  }

  static inline _ptr_type _fromObjRef(omniObjRef* o) {
    if (o)
      return (_ptr_type) o->_ptrToObjRef(_PD_repoId);
    else
      return _nil();
  }

  static _core_attr const char* _PD_repoId;

  // Other IDL defined within this scope.
  static _dyn_attr const ::CORBA::TypeCode_ptr _tc_ObjId;

  typedef char* ObjId;
  typedef ::CORBA::String_var ObjId_var;
  typedef ::CORBA::String_out ObjId_out;

  static _dyn_attr const ::CORBA::TypeCode_ptr _tc_ObjIdList;

  class ObjIdList_var;

  class ObjIdList : public _CORBA_Unbounded_Sequence_String {
  public:
    typedef ObjIdList_var _var_type;
    inline ObjIdList() {}
    inline ObjIdList(const ObjIdList& _s)
      : _CORBA_Unbounded_Sequence_String(_s) {}

    inline ObjIdList(_CORBA_ULong _max)
      : _CORBA_Unbounded_Sequence_String(_max) {}
    inline ObjIdList(_CORBA_ULong _max, _CORBA_ULong _len, char** _val, _CORBA_Boolean _rel=0)
      : _CORBA_Unbounded_Sequence_String(_max, _len, _val, _rel) {}

  

    inline ObjIdList& operator = (const ObjIdList& _s) {
      _CORBA_Unbounded_Sequence_String::operator=(_s);
      return *this;
    }
  };

  class ObjIdList_out;

  class ObjIdList_var {
  public:
    inline ObjIdList_var() : _pd_seq(0) {}
    inline ObjIdList_var(ObjIdList* _s) : _pd_seq(_s) {}
    inline ObjIdList_var(const ObjIdList_var& _s) {
      if (_s._pd_seq)  _pd_seq = new ObjIdList(*_s._pd_seq);
      else             _pd_seq = 0;
    }
    inline ~ObjIdList_var() { if (_pd_seq)  delete _pd_seq; }
      
    inline ObjIdList_var& operator = (ObjIdList* _s) {
      if (_pd_seq)  delete _pd_seq;
      _pd_seq = _s;
      return *this;
    }
    inline ObjIdList_var& operator = (const ObjIdList_var& _s) {
      if (&_s != this) {
        if (_s._pd_seq) {
          if (!_pd_seq)  _pd_seq = new ObjIdList;
          *_pd_seq = *_s._pd_seq;
        }
        else if (_pd_seq) {
          delete _pd_seq;
          _pd_seq = 0;
        }
      }
      return *this;
    }
    inline _CORBA_String_element operator [] (_CORBA_ULong _s) {
      return (*_pd_seq)[_s];
    }

  

    inline ObjIdList* operator -> () { return _pd_seq; }
    inline const ObjIdList* operator -> () const { return _pd_seq; }
#if defined(__GNUG__)
    inline operator ObjIdList& () const { return *_pd_seq; }
#else
    inline operator const ObjIdList& () const { return *_pd_seq; }
    inline operator ObjIdList& () { return *_pd_seq; }
#endif
      
    inline const ObjIdList& in() const { return *_pd_seq; }
    inline ObjIdList&       inout()    { return *_pd_seq; }
    inline ObjIdList*&      out() {
      if (_pd_seq) { delete _pd_seq; _pd_seq = 0; }
      return _pd_seq;
    }
    inline ObjIdList* _retn() { ObjIdList* tmp = _pd_seq; _pd_seq = 0; return tmp; }
      
    friend class ObjIdList_out;
    
  private:
    ObjIdList* _pd_seq;
  };

  class ObjIdList_out {
  public:
    inline ObjIdList_out(ObjIdList*& _s) : _data(_s) { _data = 0; }
    inline ObjIdList_out(ObjIdList_var& _s)
      : _data(_s._pd_seq) { _s = (ObjIdList*) 0; }
    inline ObjIdList_out(const ObjIdList_out& _s) : _data(_s._data) {}
    inline ObjIdList_out& operator = (const ObjIdList_out& _s) {
      _data = _s._data;
      return *this;
    }
    inline ObjIdList_out& operator = (ObjIdList* _s) {
      _data = _s;
      return *this;
    }
    inline operator ObjIdList*&()  { return _data; }
    inline ObjIdList*& ptr()       { return _data; }
    inline ObjIdList* operator->() { return _data; }

    inline _CORBA_String_element operator [] (_CORBA_ULong _i) {
      return (*_data)[_i];
    }

  

    ObjIdList*& _data;

  private:
    ObjIdList_out();
    ObjIdList_out& operator=(const ObjIdList_var&);
  };


};

class _objref_CORBA_InitialReferences :
  public virtual ::CORBA::Object,
  public virtual omniObjRef
{
public:
  // IDL operations
  CORBA::Object_ptr get(const char* id);
  CORBA_InitialReferences::ObjIdList* list();

  // Constructors
  inline _objref_CORBA_InitialReferences()  { _PR_setobj(0); }  // nil
  _objref_CORBA_InitialReferences(omniIOR*, omniIdentity*);

protected:
  virtual ~_objref_CORBA_InitialReferences();

  
private:
  virtual void* _ptrToObjRef(const char*);

  _objref_CORBA_InitialReferences(const _objref_CORBA_InitialReferences&);
  _objref_CORBA_InitialReferences& operator = (const _objref_CORBA_InitialReferences&);
  // not implemented

  friend class CORBA_InitialReferences;
};

class _pof_CORBA_InitialReferences : public _OMNI_NS(proxyObjectFactory) {
public:
  inline _pof_CORBA_InitialReferences() : _OMNI_NS(proxyObjectFactory)(CORBA_InitialReferences::_PD_repoId) {}
  virtual ~_pof_CORBA_InitialReferences();

  virtual omniObjRef* newObjRef(omniIOR*,omniIdentity*);
  virtual _CORBA_Boolean is_a(const char*) const;
};

class _impl_CORBA_InitialReferences :
  public virtual omniServant
{
public:
  virtual ~_impl_CORBA_InitialReferences();

  virtual CORBA::Object_ptr get(const char* id) = 0;
  virtual CORBA_InitialReferences::ObjIdList* list() = 0;
  
public:  // Really protected, workaround for xlC
  virtual _CORBA_Boolean _dispatch(omniCallHandle&);

private:
  virtual void* _ptrToInterface(const char*);
  virtual const char* _mostDerivedRepoId();
  
};


_CORBA_GLOBAL_VAR _dyn_attr const ::CORBA::TypeCode_ptr _tc_CORBA_InitialReferences;



class POA_CORBA_InitialReferences :
  public virtual _impl_CORBA_InitialReferences,
  public virtual ::PortableServer::ServantBase
{
public:
  virtual ~POA_CORBA_InitialReferences();

  inline ::CORBA_InitialReferences_ptr _this() {
    return (::CORBA_InitialReferences_ptr) _do_this(::CORBA_InitialReferences::_PD_repoId);
  }
};







#undef _core_attr
#undef _dyn_attr

void operator<<=(::CORBA::Any& _a, const CORBA_InitialReferences::ObjIdList& _s);
void operator<<=(::CORBA::Any& _a, CORBA_InitialReferences::ObjIdList* _sp);
_CORBA_Boolean operator>>=(const ::CORBA::Any& _a, CORBA_InitialReferences::ObjIdList*& _sp);
_CORBA_Boolean operator>>=(const ::CORBA::Any& _a, const CORBA_InitialReferences::ObjIdList*& _sp);

void operator<<=(::CORBA::Any& _a, CORBA_InitialReferences_ptr _s);
void operator<<=(::CORBA::Any& _a, CORBA_InitialReferences_ptr* _s);
_CORBA_Boolean operator>>=(const ::CORBA::Any& _a, CORBA_InitialReferences_ptr& _s);



inline void
CORBA_InitialReferences::_marshalObjRef(::CORBA_InitialReferences_ptr obj, cdrStream& s) {
  omniObjRef::_marshal(obj->_PR_getobj(),s);
}



#ifdef   USE_stub_in_nt_dll_NOT_DEFINED_bootstrap
# undef  USE_stub_in_nt_dll
# undef  USE_stub_in_nt_dll_NOT_DEFINED_bootstrap
#endif
#ifdef   USE_core_stub_in_nt_dll_NOT_DEFINED_bootstrap
# undef  USE_core_stub_in_nt_dll
# undef  USE_core_stub_in_nt_dll_NOT_DEFINED_bootstrap
#endif
#ifdef   USE_dyn_stub_in_nt_dll_NOT_DEFINED_bootstrap
# undef  USE_dyn_stub_in_nt_dll
# undef  USE_dyn_stub_in_nt_dll_NOT_DEFINED_bootstrap
#endif

#endif  // __bootstrap_hh__

